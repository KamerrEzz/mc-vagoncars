package com.neenbyss.client;

import com.neenbyss.client.minecarts.CarritoEntityModel;
import com.neenbyss.client.minecarts.CarritoEntityModelLayers;
import com.neenbyss.client.minecarts.CarritoEntityRenderer;
import com.neenbyss.minecarts.CarritoEntity;
import com.neenbyss.minecarts.ModEntityTypes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class MinecartsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityModelLayerRegistry.registerModelLayer(CarritoEntityModelLayers.CARRITO_MODEL, CarritoEntityModel::createBodyLayer);
		EntityRendererRegistry.register(ModEntityTypes.CARRITO, CarritoEntityRenderer::new);

		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if (client.player != null && client.player.getVehicle() instanceof CarritoEntity carrito) {
				carrito.setClientInput(client.player.input.keyPresses);
			}
		});
	}
}