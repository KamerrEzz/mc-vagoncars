package com.neenbyss.minecarts;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class SpawnCarritoItem extends Item {
	public SpawnCarritoItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();

		if (!(level instanceof ServerLevel serverLevel)) {
			return InteractionResult.SUCCESS;
		}

		BlockPos spawnPos = context.getClickedPos().relative(context.getClickedFace());
		CarritoEntity carrito = new CarritoEntity(ModEntityTypes.CARRITO, serverLevel);
		carrito.snapTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, 0.0f, 0.0f);
		serverLevel.addFreshEntity(carrito);

		return InteractionResult.SUCCESS_SERVER;
	}
}
