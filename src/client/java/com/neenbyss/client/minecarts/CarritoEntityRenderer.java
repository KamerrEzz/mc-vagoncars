package com.neenbyss.client.minecarts;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import com.neenbyss.Minecarts;
import com.neenbyss.minecarts.CarritoEntity;

import net.minecraft.client.model.EntityModel;
//? if >=26 {
import net.minecraft.client.renderer.SubmitNodeCollector;
//?} else {
/*import net.minecraft.client.renderer.MultiBufferSource;
*///?}
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
//? if >=26 {
import net.minecraft.client.renderer.state.level.CameraRenderState;
//?}
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

//? if >=26 {
public class CarritoEntityRenderer extends EntityRenderer<CarritoEntity, CarritoEntityRenderState> {
//?} else {
/*public class CarritoEntityRenderer extends EntityRenderer<CarritoEntity> {
*///?}
	private static final Identifier TEXTURE = Minecarts.id("textures/entity/carrito.png");

	private final CarritoEntityModel model;

	public CarritoEntityRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new CarritoEntityModel(context.bakeLayer(ModEntityModelLayers.CARRITO));
	}

	//? if >=26 {
	@Override
	public CarritoEntityRenderState createRenderState() {
		return new CarritoEntityRenderState();
	}

	@Override
	public void extractRenderState(CarritoEntity entity, CarritoEntityRenderState renderState, float partialTick) {
		super.extractRenderState(entity, renderState, partialTick);
		renderState.yRot = entity.getYRot(partialTick);
		renderState.wheelRotation = entity.getWheelRotation(partialTick);
	}

	@Override
	public void submit(CarritoEntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraRenderState) {
		poseStack.pushPose();

		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - renderState.yRot));
		poseStack.scale(-1.0F, -1.0F, 1.0F);
		// Blockbench's default "Java Entity" pivot convention bakes a 24-pixel vertical
		// offset into the model (see bb_main's PartPose.offset in CarritoEntityModel).
		// Vanilla mob renderers correct for this the same way; boats/minecarts don't,
		// because their models aren't authored with that convention.
		poseStack.translate(0.0F, EntityModel.MODEL_Y_OFFSET, 0.0F);
		// The model's authored front doesn't line up with the (180 - yRot) convention
		// on its own; correct with a fixed turn, same as AbstractBoatRenderer does for
		// the same reason. Flip the sign here if it ends up facing the wrong way.
		poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));

		collector.submitModel(this.model, renderState, poseStack, TEXTURE, renderState.lightCoords, OverlayTexture.NO_OVERLAY, renderState.outlineColor, null);

		poseStack.popPose();

		super.submit(renderState, poseStack, collector, cameraRenderState);
	}
	//?} else {
	/*@Override
	public void render(CarritoEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
		poseStack.pushPose();

		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));
		poseStack.scale(-1.0F, -1.0F, 1.0F);
		// Blockbench's default "Java Entity" pivot convention bakes a 24-pixel vertical
		// offset into the model (see bb_main's PartPose.offset in CarritoEntityModel).
		// Vanilla mob renderers correct for this the same way; boats/minecarts don't,
		// because their models aren't authored with that convention. 1.5F == 24px / 16,
		// the same value EntityModel.MODEL_Y_OFFSET holds on newer versions.
		poseStack.translate(0.0F, 1.5F, 0.0F);
		// The model's authored front doesn't line up with the (180 - yRot) convention
		// on its own; correct with a fixed turn, same as AbstractBoatRenderer does for
		// the same reason. Flip the sign here if it ends up facing the wrong way.
		poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));

		this.model.setupAnim(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.model.setWheelRotation(-entity.getWheelRotation(partialTick));

		var buffer = bufferSource.getBuffer(this.model.renderType(TEXTURE));
		this.model.renderToBuffer(poseStack, buffer, light, OverlayTexture.NO_OVERLAY);

		poseStack.popPose();

		super.render(entity, entityYaw, partialTick, poseStack, bufferSource, light);
	}

	@Override
	public ResourceLocation getTextureLocation(CarritoEntity entity) {
		return TEXTURE;
	}
	*///?}
}
