package org.confluence.mod.block.natural.ambience;

import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.natural.*;
import org.jetbrains.annotations.*;

public class LifeMushroom extends BaseHerbBlock {
	@Override
	public boolean mayPlaceOn(@NotNull BlockState groundState, @NotNull BlockGetter worldIn, @NotNull BlockPos pos){
		return groundState.is(Blocks.GRASS_BLOCK) || groundState.is(ModBlocks.HALLOW_GRASS_BLOCK.get());
	}
}
