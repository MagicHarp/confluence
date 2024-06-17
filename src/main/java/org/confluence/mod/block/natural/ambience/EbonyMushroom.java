package org.confluence.mod.block.natural.ambience;

import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import org.confluence.mod.block.*;
import org.confluence.mod.block.natural.*;
import org.jetbrains.annotations.*;

public class EbonyMushroom extends BaseHerbBlock {
	@Override
	public boolean mayPlaceOn(@NotNull BlockState groundState, @NotNull BlockGetter worldIn, @NotNull BlockPos pos){
		return groundState.is(ModBlocks.CORRUPT_GRASS_BLOCK.get());
	}
}
