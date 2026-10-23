package com.neenbyss.minecarts;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
		Player player = context.getPlayer();

		if (!(level instanceof ServerLevel serverLevel)) {
			return InteractionResult.SUCCESS;
		}

		BlockPos hitPos = context.getClickedPos();

		// If clicking on a block, spawn adjacent to the clicked face.
		// If clicking on air (hitPos is null/zero), spawn on the ground under the player.
		if (hitPos == null || (hitPos.getX() == 0 && hitPos.getY() == 0 && hitPos.getZ() == 0)) {
			hitPos = player.blockPosition().below();
		} else {
			hitPos = hitPos.relative(context.getClickedFace());
		}

		CarritoEntity carrito = new CarritoEntity(ModEntityTypes.CARRITO, serverLevel);
		carrito.setPos(hitPos.getX() + 0.5, hitPos.getY() + 0.0625, hitPos.getZ() + 0.5);
		serverLevel.addFreshEntity(carrito);

		return InteractionResult.SUCCESS_SERVER;
	}
}
