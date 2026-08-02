package com.neenbyss.minecarts;

import com.neenbyss.Minecarts;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

public class ModItems {
	public static final ResourceKey<Item> TALADRO_CARRITO_KEY = ResourceKey.create(Registries.ITEM, Minecarts.id("taladro_carrito"));

	public static final Item TALADRO_CARRITO = new Item(new Item.Properties().setId(TALADRO_CARRITO_KEY));

	public static void registerModItems() {
		Registry.register(BuiltInRegistries.ITEM, TALADRO_CARRITO_KEY, TALADRO_CARRITO);

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
				.register(entries -> entries.accept(TALADRO_CARRITO));
	}
}
