package com.neenbyss.minecarts.modules;

import com.neenbyss.minecarts.CarritoEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import netnet.minecraft.world.phys.Vec3;

public class ExplosiveModule implements CarritoModule {
    private float explosionRadius = 3.0f;
    private float explosionPower = 3.0f;
    private float fallThreshold = -1.5f; 

    public ExplosiveModule() {
    }

    @Override
    public String getModuleId() {
        return "explosive";
    }

    @Override
    public void onTick(CarritoEntity entity, ServerLevel level) {
        // Detect high velocity fall
        if (entity.getDeltaMovement().y < fallThreshold) {
            // We don't explode immediately to avoid instant death on slight bumps
            // but we mark it for the next tick or handled by damage logic
        }
    }

    @Override
    public void onDamage(CarritoEntity entity, DamageSource source, float amount) {
        // If health drops to 0, explode
        if (entity.getHealth() <= 0) {
            ServerLevel level = (ServerLevel) entity.level();
            level.explode(null, entity.getX(), entity.getY(), entity.getZ(), explosionPower, Explosion.Mode.BLOCK);
        }
    }

    @Override
    public void onInteract(CarritoEntity entity, Player player) {
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putFloat("radius", explosionRadius);
        nbt.putFloat("power", explosionPower);
        nbt.putFloat("fall_threshold", fallThreshold);
    }

    @Override
    public void readNBT(CompoundTag nbt) {
        this.explosionRadius = nbt.getFloat("radius");
        this.explosionPower = nbt.getFloat("power");
        this.fallThreshold = nbt.getFloat("fall_threshold");
    }
}
