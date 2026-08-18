package com.neenbyss.minecarts;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

public class CarritoEntity extends Entity {
	private static final float MOVE_SPEED = 0.3f;
	private static final float TURN_SPEED = 5.0f;
	// Radians of wheel spin per tick per (block/tick) of horizontal speed. Purely
	// cosmetic — tuned by eye, not tied to the model's actual wheel radius.
	private static final float WHEEL_ROTATION_SPEED = 4.0f;

	private float wheelRotO;
	private float wheelRot;

	// Set every client tick by MinecartsClient from the local player's raw key
	// state. Needed because the client predicts this entity's movement locally
	// (since it has a controlling passenger) and reports it back to the server
	// via ServerboundMoveVehiclePacket — without this, the client's own copy of
	// yRot never advances and that packet stomps the server's rotation back to 0.
	private Input clientInput = Input.EMPTY;

	public CarritoEntity(EntityType<? extends CarritoEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		// Placeholder: no synced data yet.
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		// Placeholder: nothing persisted yet.
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		// Placeholder: nothing persisted yet.
	}

	@Override
	public InteractionResult interact(Player player, InteractionHand hand, Vec3 hitVec) {
		if (!this.level().isClientSide() && this.getPassengers().isEmpty()) {
			player.startRiding(this);
		}

		return InteractionResult.SUCCESS;
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
		// Entity's own default is false — without this, right-click raycasts skip over it entirely.
		return !this.isRemoved();
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
		// Placeholder: no damage/destruction handling yet.
		return false;
	}

	@Override
	protected double getDefaultGravity() {
		// Entity's own default is 0.0 (no gravity) — only specific subclasses opt in.
		// Reusing AbstractBoat's constant since it's the closest vanilla reference.
		return 0.04;
	}

	@Override
	public float maxUpStep() {
		// Entity's own default is 0.0 — without this, move() treats a one-block
		// ledge as a wall and the entity can descend slopes but not climb them.
		return 1.0f;
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
}
