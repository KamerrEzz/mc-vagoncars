package com.neenbyss.minecarts;

import com.neenbyss.Minecarts;

//? if >=26 {
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
//?} else {
/*import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
*///?}

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

public class ModItems {
	public static final ResourceKey<Item> TALADRO_CARRITO_KEY = ResourceKey.create(Registries.ITEM, Minecarts.id("taladro_carrito"));

	//? if >=26 {
	public static final Item TALADRO_CARRITO = new Item(new Item.Properties().setId(TALADRO_CARRITO_KEY));
	//?} else {
	/*public static final Item TALADRO_CARRITO = new Item(new Item.Properties());
	*///?}

	public static final ResourceKey<Item> SPAWN_CARRITO_KEY = ResourceKey.create(Registries.ITEM, Minecarts.id("spawn_carrito"));

	//? if >=26 {
	public static final Item SPAWN_CARRITO = new SpawnCarritoItem(new Item.Properties().setId(SPAWN_CARRITO_KEY));
	//?} else {
	/*public static final Item SPAWN_CARRITO = new SpawnCarritoItem(new Item.Properties());
	*///?}

	public static void registerModItems() {
		Registry.register(BuiltInRegistries.ITEM, TALADRO_CARRITO_KEY, TALADRO_CARRITO);
		Registry.register(BuiltInRegistries.ITEM, SPAWN_CARRITO_KEY, SPAWN_CARRITO);

		//? if >=26 {
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
				.register(entries -> {
					entries.accept(TALADRO_CARRITO);
					entries.accept(SPAWN_CARRITO);
				});
		//?} else {
		/*ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
				.register(entries -> {
					entries.accept(TALADRO_CARRITO);
					entries.accept(SPAWN_CARRITO);
				});
		*///?}
	}
}
