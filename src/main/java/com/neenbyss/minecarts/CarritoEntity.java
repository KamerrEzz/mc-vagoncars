package com.neenbyss.minecarts;

import com.neenbyss.minecarts.modules.CarritoModule;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.InterpolationHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import java.util.ArrayList;
import java.util.List;

public class CarritoEntity extends Entity {
	private static final float MOVE_SPEED = 0.3f;
	private static final float TURN_SPEED = 5.0f;
	private static final float WHEEL_ROTATION_SPEED = 4.0f;

	private float wheelRotO;
	private float wheelRot;

	private final InterpolationHandler interpolation = new InterpolationHandler(this);
	private Input clientInput = Input.EMPTY;

	private final List<CarritoModule> modules = new ArrayList<>();

	public CarritoEntity(EntityType<? extends CarritoEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		CompoundTag nbt = input.getCompound("Modules");
		if (nbt.contains("List")) {
			for (int i = 0; i < nbt.getList("List", 10).size(); i++) {
				CompoundTag tag = nbt.getCompound("List", i);
				String type = tag.getString("type");
				CarritoModule module = createModuleByType(type);
				if (module != null) {
					module.readNBT(tag.getCompound("data"));
					this.modules.add(module);
				}
			}
		}
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		CompoundTag nbt = output.getCompound("Modules");
		nbt.putList("List", this.modules);
	}

	private CarritoModule createModuleByType(String type) {
		return switch (type) {
			case "drill" -> new com.neenbyss.minecarts.modules.DrillModule("default");
			case "storage" -> new com.neenbyss.minecarts.modules.StorageModule();
			case "explosive" -> new com.neenbyss.minecarts.modules.ExplosiveModule();
			default -> null;
		};
	}

	@Override
	public InteractionResult interact(Player player, InteractionHand hand, Vec3 hitVec) {
		if (!player.level().isClientSide() && this.getPassengers().isEmpty()) {
			if (player.distanceToSqr(this) <= 9.0) {
				player.startRiding(this);
				return InteractionResult.SUCCESS;
			}
		}

		return InteractionResult.PASS;
	}

	@Override
	public LivingEntity getControllingPassenger() {
		return this.getFirstPassenger() instanceof Player player ? player : null;
	}

	@Override
	public boolean isPushable() {
		return true;
	}

	@Override
	public boolean isPickable() {
		return !this.isRemoved();
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
		for (CarritoModule module : modules) {
			module.onDamage(this, damageSource, amount);
		}
		return super.hurtServer(level, damageSource, amount);
	}

	@Override
	protected double getDefaultGravity() {
		return 0.04;
	}

	@Override
	public float maxUpStep() {
		return 1.0f;
	}

	@Override
	public InterpolationHandler getInterpolation() {
		return this.interpolation;
	}

	public void setClientInput(Input input) {
		this.clientInput = input;
	}

	public float getWheelRotation(float partialTick) {
		return Mth.lerp(partialTick, this.wheelRotO, this.wheelRot);
	}

	@Override
	public void tick() {
		super.tick();

		this.interpolation.interpolate();

		if (this.level().isClientSide() && !this.isLocalInstanceAuthoritative()) {
			this.wheelRotO = this.wheelRot;
			this.wheelRot += (float) this.getDeltaMovement().horizontalDistance() * WHEEL_ROTATION_SPEED;
			return;
		}

		if (!this.onGround()) {
			this.applyGravity();
		}

		if (this.getControllingPassenger() instanceof ServerPlayer serverPlayer) {
			this.driveFromInput(serverPlayer.getLastClientInput());
		} else if (this.level().isClientSide() && this.getControllingPassenger() != null) {
			this.driveFromInput(this.clientInput);
		} else {
			this.applyGroundFriction();
		}

		this.move(MoverType.SELF, this.getDeltaMovement());

		this.wheelRotO = this.wheelRot;
		this.wheelRot += (float) this.getDeltaMovement().horizontalDistance() * WHEEL_ROTATION_SPEED;

		if (!this.level().isClientSide()) {
			for (CarritoModule module : modules) {
				module.onTick(this, (ServerLevel) this.level());
			}
		}
	}

	private void driveFromInput(Input input) {
		if (input.left()) {
			this.setYRot(this.getYRot() - TURN_SPEED);
		}
		if (input.right()) {
			this.setYRot(this.getYRot() + TURN_SPEED);
		}

		float forward = 0.0f;
		if (input.forward()) {
			forward += 1.0f;
		}
		if (input.backward()) {
			forward -= 1.0f;
		}

		if (forward != 0.0f) {
			double yawRad = Math.toRadians(this.getYRot());
			double dx = -Math.sin(yawRad) * forward * MOVE_SPEED;
			double dz = Math.cos(yawRad) * forward * MOVE_SPEED;
			this.setDeltaMovement(dx, this.getDeltaMovement().y, dz);
		} else {
			this.applyGroundFriction();
		}
	}

	private void applyGroundFriction() {
		Vec3 currentMotion = this.getDeltaMovement();
		this.setDeltaMovement(currentMotion.x * 0.8, currentMotion.y, currentMotion.z * 0.8);
	}

    public List<CarritoModule> getModules() {
        return modules;
    }

    public void addModule(CarritoModule module) {
        this.modules.add(module);
    }
}