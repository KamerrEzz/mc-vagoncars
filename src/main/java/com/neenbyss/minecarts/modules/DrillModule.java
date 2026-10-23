package com.neenbyss.minecarts.modules;

import com.neenbyss.minecarts.CarritoEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block.Type;

public class DrillModule implements CarritoModule {
    private String drillId;
    private boolean active = false;
    private int drillCooldown = 0;
    private int drillInterval = 4; // Ticks between blocks (4 ticks = 5 blocks per second)
    private boolean destroysEntities = false;

    public DrillModule(String drillId) {
        this.drillId = drillId;
    }

    @Override
    public String getModuleId() {
        return "drill";
    }

    @Override
    public void onTick(CarritoEntity entity, ServerLevel level) {
        if (!active) return;

        if (drillCooldown > 0) {
            drillCooldown--;
            return;
        }

        // Only drill if the minecart is moving
        if (entity.getDeltaMovement().horizontalDistance() < 0.01) {
            return;
        }

        Vec3 lookVec = entity.getLookAngle();
        double reach = 2.0;
        Vec3 target = entity.position().add(lookVec.scale(reach));

        BlockHitResult hit = level.clip(new ClipContext(
            entity.getEyePosition(),
            target,
            Type.BLOCK,
            false,
            entity
        ));

        if (hit.getType() == BlockHitResult.Type.BLOCK) {
            // Prevent breaking bedrock or unbreakable blocks to avoid crashes
            BlockState hitState = level.getBlockState(hit.getPos());
            if (hitState.is(Blocks.BEDROCK)) {
                return;
            }

            // Destroy the block and update the client/server state
            level.destroyBlock(hit.getPos(), false);
            
            // Reset cooldown
            drillCooldown = drillInterval;
        }
    }

    @Override
    public void onDamage(CarritoEntity entity, DamageSource source, float amount) {
    }

    @Override
    public void onInteract(CarritoEntity entity, Player player) {
        this.active = !this.active;
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("drill_id", drillId);
        nbt.putBoolean("active", active);
        nbt.putInt("drill_interval", drillInterval);
    }

    @Override
    public void readNBT(CompoundTag nbt) {
        this.drillId = nbt.getString("drill_id");
        this.active = nbt.getBoolean("active");
        this.drillInterval = nbt.getInt("drill_interval");
    }
}