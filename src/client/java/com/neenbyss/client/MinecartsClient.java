package com.neenbyss.client;

import com.neenbyss.client.minecarts.CarritoEntityModel;
import com.neenbyss.client.minecarts.CarritoEntityRenderer;
import com.neenbyss.client.minecarts.ModEntityModelLayers;
import com.neenbyss.minecarts.CarritoEntity;
import com.neenbyss.minecarts.ModEntityTypes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
//? if >=26 {
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;

import net.minecraft.client.renderer.entity.EntityRenderers;
//?} else {
/*import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
*///?}

public class MinecartsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		//? if >=26 {
		ModelLayerRegistry.registerModelLayer(ModEntityModelLayers.CARRITO, CarritoEntityModel::createBodyLayer);
		EntityRenderers.register(ModEntityTypes.CARRITO, CarritoEntityRenderer::new);
		//?} else {
		/*EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.CARRITO, CarritoEntityModel::createBodyLayer);
		EntityRendererRegistry.register(ModEntityTypes.CARRITO, CarritoEntityRenderer::new);
		*///?}

		//? if >=26 {
		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if (client.player != null && client.player.getVehicle() instanceof CarritoEntity carrito) {
				carrito.setClientInput(client.player.input.keyPresses);
			}
		});
		//?}
	}
}