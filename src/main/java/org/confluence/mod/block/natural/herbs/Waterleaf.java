package org.confluence.mod.block.natural.herbs;

import net.minecraft.core.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.natural.*;
import org.confluence.mod.item.*;
import org.jetbrains.annotations.*;

public class Waterleaf extends BaseHerbBlock {

	@Override
	public boolean mayPlaceOn(@NotNull BlockState groundState, @NotNull BlockGetter worldIn, @NotNull BlockPos pos){
		return groundState.is(Blocks.SAND) || groundState.is(Blocks.RED_SAND) || groundState.is(ModBlocks.PEARL_SAND.get());
	}

	@Override
	protected @NotNull ItemLike getBaseSeedId(){
		return ModItems.WATERLEAF_SEED.get();
	}

	@Override
	public boolean canBloom(ServerLevel world, BlockState state){
		return world.isRaining();
	}
}
