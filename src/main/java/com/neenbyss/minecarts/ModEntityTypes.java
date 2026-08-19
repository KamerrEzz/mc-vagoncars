package com.neenbyss.minecarts;

import com.neenbyss.Minecarts;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntityTypes {
	public static final ResourceKey<EntityType<?>> CARRITO_KEY = ResourceKey.create(Registries.ENTITY_TYPE, Minecarts.id("carrito"));

	public static final EntityType<CarritoEntity> CARRITO = EntityType.Builder.<CarritoEntity>of(CarritoEntity::new, MobCategory.MISC)
			.sized(1.0f, 1.0f)
			.clientTrackingRange(8)
			.updateInterval(3)
			//? if >=26 {
			.build(CARRITO_KEY);
			//?} else {
			/*.build("carrito");
			*///?}

	public static void registerModEntityTypes() {
		Registry.register(BuiltInRegistries.ENTITY_TYPE, CARRITO_KEY, CARRITO);
	}
}
