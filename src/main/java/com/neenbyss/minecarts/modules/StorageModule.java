package com.neenbyss.minecarts.modules;

import com.neenbyss.minecarts.CarritoEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class StorageModule implements CarritoModule {
    private List<ItemStack> inventory = new ArrayList<>();
    private List<String> filterList = new ArrayList<>();

    public StorageModule() {
    }

    @Override
    public String getModuleId() {
        return "storage";
    }

    @Override
    public void onTick(CarritoEntity entity, ServerLevel level) {
    }

    @Override
    public void onDamage(CarritoEntity entity, DamageSource source, float amount) {
    }

    @Override
    public void onInteract(CarritoEntity entity, Player player) {
        // Logic for opening the inventory GUI would go here
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("filter_list", String.join(",", filterList));
        // In a real implementation, we would serialize the ItemStack list properly
    }

    @Override
    public void readNBT(CompoundTag nbt) {
        if (nbt.contains("filter_list")) {
            String[] items = nbt.getString("filter_list").split(",");
            for (String item : items) {
                if (!item.isEmpty()) filterList.add(item);
            }
        }
    }
}
