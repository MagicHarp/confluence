package org.confluence.mod.block.natural.herbs;

import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import org.confluence.mod.block.natural.*;
import org.confluence.mod.item.*;
import org.jetbrains.annotations.*;

public class MoonshineGrass extends BaseHerbBlock {

	@Override
	public boolean mayPlaceOn(@NotNull BlockState groundState, @NotNull BlockGetter worldIn, @NotNull BlockPos pos){
		return groundState.is(Blocks.GRASS_BLOCK); // TODO: 丛林草
	}

	@Override
	protected @NotNull ItemLike getBaseSeedId(){
		return ModItems.MOONSHINE_GRASS_SEED.get();
	}

	@Override
	public boolean canBloom(ServerLevel world, BlockState state){
		return world.isNight();
	}
}
