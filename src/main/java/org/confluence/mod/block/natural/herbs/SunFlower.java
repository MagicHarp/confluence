package org.confluence.mod.block.natural.herbs;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.natural.BaseHerbBlock;
import org.confluence.mod.item.ModItems;
import org.jetbrains.annotations.NotNull;

public class SunFlower extends BaseHerbBlock {
	@Override
	protected @NotNull ItemLike getBaseSeedId(){
		return ModItems.SUNFLOWERS_SEED.get();
	}

	@Override
	public boolean canBloom(ServerLevel world, BlockState state){
		return world.isDay();
	}
}
