package com.neenbyss.minecarts.modules;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import com.neenbyss.minecarts.CarritoEntity;

public interface CarritoModule {
    String getModuleId();
    void onTick(CarritoEntity entity, ServerLevel level);
    void onDamage(CarritoEntity entity, DamageSource source, float amount);
    void onInteract(CarritoEntity entity, Player player);
    void writeNBT(CompoundTag nbt);
    void readNBT(CompoundTag nbt);
}
