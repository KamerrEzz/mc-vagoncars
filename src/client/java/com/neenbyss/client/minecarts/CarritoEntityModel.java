package com.neenbyss.client.minecarts;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class CarritoEntityModel extends EntityModel<CarritoEntityRenderState> {
	private final ModelPart bbMain;
	private final ModelPart llantaDelDer;
	private final ModelPart llantaDelIzq;
	private final ModelPart llantaTrasIzq;
	private final ModelPart llantaTrasDer;

	public CarritoEntityModel(ModelPart root) {
		super(root);
		this.bbMain = root.getChild("bb_main");
		this.llantaDelDer = this.bbMain.getChild("llanta_del_der_r1");
		this.llantaDelIzq = this.bbMain.getChild("llanta_del_izq_r1");
		this.llantaTrasIzq = this.bbMain.getChild("llanta_del_tras_izq_r1");
		this.llantaTrasDer = this.bbMain.getChild("llanta_del_tras_der_r1");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();

		PartDefinition bbMain = partDefinition.addOrReplaceChild("bb_main",
				CubeListBuilder.create().texOffs(0, 0)
						.addBox(-6.0F, -6.0F, -3.0F, 12.0F, 4.0F, 6.0F, CubeDeformation.NONE),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		bbMain.addOrReplaceChild("llanta_del_der_r1",
				CubeListBuilder.create().texOffs(12, 18)
						.addBox(-1.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, CubeDeformation.NONE),
				PartPose.offsetAndRotation(5.0F, -2.0F, 3.0F, 0.0F, 1.5708F, 0.0F));

		bbMain.addOrReplaceChild("llanta_del_tras_der_r1",
				CubeListBuilder.create().texOffs(0, 18)
						.addBox(-1.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, CubeDeformation.NONE),
				PartPose.offsetAndRotation(-5.0F, -2.0F, 3.0F, 0.0F, 1.5708F, 0.0F));

		bbMain.addOrReplaceChild("llanta_del_tras_izq_r1",
				CubeListBuilder.create().texOffs(12, 10)
						.addBox(-1.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, CubeDeformation.NONE),
				PartPose.offsetAndRotation(-5.0F, -2.0F, -3.0F, 0.0F, 1.5708F, 0.0F));

		bbMain.addOrReplaceChild("llanta_del_izq_r1",
				CubeListBuilder.create().texOffs(0, 10)
						.addBox(-1.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, CubeDeformation.NONE),
				PartPose.offsetAndRotation(5.0F, -2.0F, -3.0F, 0.0F, 1.5708F, 0.0F));

		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	@Override
	public void setupAnim(CarritoEntityRenderState renderState) {
		super.setupAnim(renderState);

		float wheelSpin = renderState.wheelRotation;
		this.llantaDelDer.xRot = wheelSpin;
		this.llantaDelIzq.xRot = wheelSpin;
		this.llantaTrasIzq.xRot = wheelSpin;
		this.llantaTrasDer.xRot = wheelSpin;
	}
}
