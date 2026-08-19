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
//? if >=26 {
import net.minecraft.world.entity.InterpolationHandler;
//?}
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
//? if >=26 {
import net.minecraft.world.entity.player.Input;
//?}
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
//? if >=26 {
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
//?} else {
/*import net.minecraft.nbt.CompoundTag;
*///?}
import net.minecraft.world.phys.Vec3;

public class CarritoEntity extends Entity {
	private static final float MOVE_SPEED = 0.3f;
	private static final float TURN_SPEED = 5.0f;
	// Radians of wheel spin per tick per (block/tick) of horizontal speed. Purely
	// cosmetic — tuned by eye, not tied to the model's actual wheel radius.
	private static final float WHEEL_ROTATION_SPEED = 4.0f;

	private float wheelRotO;
	private float wheelRot;

	// Lets non-controlling clients (bystanders) smoothly interpolate toward the
	// server-synced position instead of snapping every time a position packet
	// arrives — same interpolation scheme AbstractBoat uses (InterpolationHandler
	// on 26.1+, a manual step-lerp on older versions). See tick(), and
	// getInterpolation() (26.1+) or lerpTo()/tickLerp() (older) below.
	//? if >=26 {
	private final InterpolationHandler interpolation = new InterpolationHandler(this);
	//?} else {
	/*private int lerpSteps;
	private double lerpX;
	private double lerpY;
	private double lerpZ;
	private double lerpYRot;
	private double lerpXRot;
	*///?}

	//? if >=26 {
	// Set every client tick by MinecartsClient from the local player's raw key
	// state. Needed because the client predicts this entity's movement locally
	// (since it has a controlling passenger) and reports it back to the server
	// via ServerboundMoveVehiclePacket — without this, the client's own copy of
	// yRot never advances and that packet stomps the server's rotation back to 0.
	private Input clientInput = Input.EMPTY;
	//?}

	public CarritoEntity(EntityType<? extends CarritoEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		// Placeholder: no synced data yet.
	}

	//? if >=26 {
	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		// Placeholder: nothing persisted yet.
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		// Placeholder: nothing persisted yet.
	}
	//?} else {
	/*@Override
	protected void readAdditionalSaveData(CompoundTag input) {
		// Placeholder: nothing persisted yet.
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag output) {
		// Placeholder: nothing persisted yet.
	}
	*///?}

	//? if >=26 {
	@Override
	public InteractionResult interact(Player player, InteractionHand hand, Vec3 hitVec) {
		if (!this.level().isClientSide() && this.getPassengers().isEmpty()) {
			player.startRiding(this);
		}

		return InteractionResult.SUCCESS;
	}
	//?} else {
	/*@Override
	public InteractionResult interact(Player player, InteractionHand hand) {
		if (!this.level().isClientSide() && this.getPassengers().isEmpty()) {
			player.startRiding(this);
		}

		return InteractionResult.SUCCESS;
	}
	*///?}

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

	//? if >=26 {
	@Override
	public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
		// Placeholder: no damage/destruction handling yet.
		return false;
	}
	//?} else {
	/*@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		// Placeholder: no damage/destruction handling yet.
		return false;
	}
	*///?}

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

	//? if >=26 {
	@Override
	public InterpolationHandler getInterpolation() {
		return this.interpolation;
	}
	//?} else {
	/*@Override
	public void lerpTo(double x, double y, double z, float yRot, float xRot, int steps) {
		this.lerpX = x;
		this.lerpY = y;
		this.lerpZ = z;
		this.lerpYRot = yRot;
		this.lerpXRot = xRot;
		this.lerpSteps = steps;
	}

	private void tickLerp() {
		if (this.lerpSteps <= 0) {
			return;
		}

		this.setPos(
				this.getX() + (this.lerpX - this.getX()) / this.lerpSteps,
				this.getY() + (this.lerpY - this.getY()) / this.lerpSteps,
				this.getZ() + (this.lerpZ - this.getZ()) / this.lerpSteps);
		this.setYRot((float) (this.getYRot() + Mth.wrapDegrees(this.lerpYRot - this.getYRot()) / this.lerpSteps));
		this.setXRot((float) (this.getXRot() + (this.lerpXRot - this.getXRot()) / this.lerpSteps));
		this.lerpSteps--;
	}
	*///?}

	//? if >=26 {
	public void setClientInput(Input input) {
		this.clientInput = input;
	}
	//?}

	public float getWheelRotation(float partialTick) {
		return Mth.lerp(partialTick, this.wheelRotO, this.wheelRot);
	}

	@Override
	public void tick() {
		super.tick();

		//? if >=26 {
		this.interpolation.interpolate();

		if (this.level().isClientSide() && !this.isLocalInstanceAuthoritative()) {
		//?} else {
		/*if (this.level().isClientSide() && !this.isControlledByLocalInstance()) {
		*///?}
			// A bystander client (someone else is driving, or nobody is). Don't run
			// local physics here — it would fight the server-authoritative position
			// with stale/empty input. Let interpolation carry it instead.
			this.wheelRotO = this.wheelRot;
			this.wheelRot += (float) this.getDeltaMovement().horizontalDistance() * WHEEL_ROTATION_SPEED;
			//? if <26 {
			/*this.tickLerp();
			*///?}
			return;
		}

		if (!this.onGround()) {
			this.applyGravity();
		}

		//? if >=26 {
		if (this.getControllingPassenger() instanceof ServerPlayer serverPlayer) {
			this.driveFromInput(serverPlayer.getLastClientInput());
		} else if (this.level().isClientSide() && this.getControllingPassenger() != null) {
			this.driveFromInput(this.clientInput);
		} else {
			this.applyGroundFriction();
		}
		//?} else {
		/*if (this.getControllingPassenger() instanceof Player player) {
			// xxa/zza are populated by vanilla for the controlling player on both
			// sides — client-predicted from local key state before any packet round
			// trip, server-authoritative from the synced movement-input packet —
			// which is exactly the pre-Input-class equivalent of getLastClientInput().
			this.driveFromInput(player);
		} else {
			this.applyGroundFriction();
		}
		*///?}

		this.move(MoverType.SELF, this.getDeltaMovement());

		this.wheelRotO = this.wheelRot;
		this.wheelRot += (float) this.getDeltaMovement().horizontalDistance() * WHEEL_ROTATION_SPEED;
	}

	//? if >=26 {
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
	//?} else {
	/*private void driveFromInput(Player player) {
		if (player.xxa < 0.0f) {
			this.setYRot(this.getYRot() - TURN_SPEED);
		}
		if (player.xxa > 0.0f) {
			this.setYRot(this.getYRot() + TURN_SPEED);
		}

		float forward = Math.signum(player.zza);

		if (forward != 0.0f) {
			double yawRad = Math.toRadians(this.getYRot());
			double dx = -Math.sin(yawRad) * forward * MOVE_SPEED;
			double dz = Math.cos(yawRad) * forward * MOVE_SPEED;
			this.setDeltaMovement(dx, this.getDeltaMovement().y, dz);
		} else {
			this.applyGroundFriction();
		}
	}
	*///?}

	private void applyGroundFriction() {
		Vec3 currentMotion = this.getDeltaMovement();
		this.setDeltaMovement(currentMotion.x * 0.8, currentMotion.y, currentMotion.z * 0.8);
	}
}
