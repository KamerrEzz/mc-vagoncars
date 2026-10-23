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

public class CarritoEntityModel extends EntityModel<CarritoEntity> {
    private final ModelPart body_bottom;
    private final ModelPart body_wall_left;
    private final ModelPart body_wall_right;
    private final ModelPart body_front;
    private final ModelPart body_back;
    private final ModelPart wheel1;
    private final ModelPart wheel2;
    private final ModelPart wheel3;
    private final ModelPart wheel4;

    public CarritoEntityModel(ModelPart root) {
        this.body_bottom = root.getChild("body_bottom");
        this.body_wall_left = root.getChild("body_wall_left");
        this.body_wall_right = root.getChild("body_wall_right");
        this.body_front = root.getChild("body_front");
        this.body_back = root.getChild("body_back");
        this.wheel1 = root.getChild("wheel1");
        this.wheel2 = root.getChild("wheel2");
        this.wheel3 = root.getChild("wheel3");
        this.wheel4 = root.getChild("wheel4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdef = new MeshDefinition();
        PartDefinition root = meshdef.getRoot();

        // Base plate
        root.addOrReplaceChild("body_bottom",
            CubeListBuilder.create().addBox(-4.0F, 0.0F, -5.6F, 8, 2.4F, 5.6F),
            PartPose.ZERO);

        // Left wall
        root.addOrReplaceChild("body_wall_left",
            CubeListBuilder.create().addBox(-4.0F, 2.4F, -5.6F, 2.4F, 3.2F, 5.6F),
            PartPose.ZERO);

        // Right wall
        root.addOrReplaceChild("body_wall_right",
            CubeListBuilder.create().addBox(1.6F, 2.4F, -5.6F, 2.4F, 3.2F, 5.6F),
            PartPose.ZERO);

        // Front panel
        root.addOrReplaceChild("body_front",
            CubeListBuilder.create().addBox(-4.0F, 2.4F, 2.8F, 8, 1.6F, 2.8F),
            PartPose.ZERO);

        // Back panel
        root.addOrReplaceChild("body_back",
            CubeListBuilder.create().addBox(-4.0F, 2.4F, -5.6F, 8, 1.6F, 2.8F),
            PartPose.ZERO);

        // Wheels
        root.addOrReplaceChild("wheel1",
            CubeListBuilder.create().addBox(-2.4F, 0.4F, -2.4F, 0.8F, 1.6F, 1.6F),
            PartPose.ZERO);

        root.addOrReplaceChild("wheel2",
            CubeListBuilder.create().addBox(1.6F, 0.4F, -2.4F, 0.8F, 1.6F, 1.6F),
            PartPose.ZERO);

        root.addOrReplaceChild("wheel3",
            CubeListBuilder.create().addBox(-2.4F, 0.4F, 0.8F, 0.8F, 1.6F, 1.6F),
            PartPose.ZERO);

        root.addOrReplaceChild("wheel4",
            CubeListBuilder.create().addBox(1.6F, 0.4F, 0.8F, 0.8F, 1.6F, 1.6F),
            PartPose.ZERO);

        return LayerDefinition.create(meshdef, 64, 64);
    }

    @Override
    public void setupAnim(CarritoEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float wheelRot = entity.getWheelRotation(netHeadYaw);
        wheel1.yRot = wheelRot * 0.25f;
        wheel2.yRot = wheelRot * 0.25f;
        wheel3.yRot = wheelRot * 0.25f;
        wheel4.yRot = wheelRot * 0.25f;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        body_bottom.render(poseStack, buffer, packedLight, packedOverlay, color);
        body_wall_left.render(poseStack, buffer, packedLight, packedOverlay, color);
        body_wall_right.render(poseStack, buffer, packedLight, packedOverlay, color);
        body_front.render(poseStack, buffer, packedLight, packedOverlay, color);
        body_back.render(poseStack, buffer, packedLight, packedOverlay, color);
        wheel1.render(poseStack, buffer, packedLight, packedOverlay, color);
        wheel2.render(poseStack, buffer, packedLight, packedOverlay, color);
        wheel3.render(poseStack, buffer, packedLight, packedOverlay, color);
        wheel4.render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}