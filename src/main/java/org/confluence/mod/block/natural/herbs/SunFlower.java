package org.confluence.mod.block.natural.herbs;

import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.natural.*;
import org.confluence.mod.item.*;
import org.jetbrains.annotations.*;

public class SunFlower extends BaseHerbBlock {

	@Override
	public boolean mayPlaceOn(@NotNull BlockState groundState, @NotNull BlockGetter worldIn, @NotNull BlockPos pos){
		return groundState.is(Blocks.GRASS_BLOCK) || groundState.is(ModBlocks.HALLOW_GRASS_BLOCK.get());
	}

	@Override
	protected @NotNull ItemLike getBaseSeedId(){
		return ModItems.SUNFLOWERS_SEED.get();
	}

	@Override
	public boolean canBloom(ServerLevel world, BlockState state){
		return world.isDay();
	}
}
