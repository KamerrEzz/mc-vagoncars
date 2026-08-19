package com.neenbyss.client.minecarts;

//? if >=26 {
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class CarritoEntityRenderState extends EntityRenderState {
	public float yRot;
	public float wheelRotation;
}
//?} else {
/*// Unused before the render-state split (introduced 1.21.2) — CarritoEntityRenderer
// reads straight off the entity instead. Kept as an empty placeholder so this file
// still compiles standalone.
public class CarritoEntityRenderState {
}
*///?}
