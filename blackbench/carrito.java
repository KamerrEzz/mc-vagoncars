// Made with Blockbench 5.1.6
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class carrito extends EntityModel<CarritoEntity> {
	private final ModelPart bb_main;
	public carrito(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -6.0F, -3.0F, 12.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData llanta_del_der_r1 = bb_main.addChild("llanta_del_der_r1", ModelPartBuilder.create().uv(12, 18).cuboid(-1.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, -2.0F, 3.0F, 0.0F, 1.5708F, 0.0F));

		ModelPartData llanta_del_tras_der_r1 = bb_main.addChild("llanta_del_tras_der_r1", ModelPartBuilder.create().uv(0, 18).cuboid(-1.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, -2.0F, 3.0F, 0.0F, 1.5708F, 0.0F));

		ModelPartData llanta_del_tras_izq_r1 = bb_main.addChild("llanta_del_tras_izq_r1", ModelPartBuilder.create().uv(12, 10).cuboid(-1.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, -2.0F, -3.0F, 0.0F, 1.5708F, 0.0F));

		ModelPartData llanta_del_izq_r1 = bb_main.addChild("llanta_del_izq_r1", ModelPartBuilder.create().uv(0, 10).cuboid(-1.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, -2.0F, -3.0F, 0.0F, 1.5708F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(CarritoEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}