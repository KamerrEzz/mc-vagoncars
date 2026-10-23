package com.neenbyss.client.minecarts;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.neenbyss.minecarts.CarritoEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class CarritoEntityRenderer extends EntityRenderer<CarritoEntity> {
    private final EntityModel<CarritoEntity> model;

    public CarritoEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new CarritoEntityModel(context.bakeLayer(CarritoEntityModelLayers.CARRITO_MODEL));
        shadowSize = 0.7f;
    }

    @Override
    public ResourceLocation getTextureLocation(CarritoEntity entity) {
        return new ResourceLocation("minecarts", "textures/entity/minecart_body.png");
    }

    @Override
    public void render(CarritoEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0f, 0.375f, 0.0f);
        poseStack.mulPose(Axis.YP.rotationDegrees(entity.getYRot()));

        model.setupAnim(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        model.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(entity))),
                           packedLight, 0, 0xffffff);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}