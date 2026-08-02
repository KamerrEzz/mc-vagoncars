package com.neenbyss.client.minecarts;

import com.neenbyss.minecarts.CarritoEntity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class CarritoEntityRenderer extends EntityRenderer<CarritoEntity, EntityRenderState> {
	public CarritoEntityRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public EntityRenderState createRenderState() {
		return new EntityRenderState();
	}
}
