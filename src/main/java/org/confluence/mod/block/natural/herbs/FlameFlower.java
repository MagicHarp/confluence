package org.confluence.mod.block.natural.herbs;

import net.minecraft.core.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import org.confluence.mod.block.*;
import org.confluence.mod.block.natural.*;
import org.confluence.mod.item.*;
import org.jetbrains.annotations.*;

public class FlameFlower extends BaseHerbBlock {
	@Override
	public boolean mayPlaceOn(@NotNull BlockState groundState, @NotNull BlockGetter worldIn, @NotNull BlockPos pos){
		return groundState.is(ModBlocks.ASH_BLOCK.get()); // TODO: 灰烬草
	}

	@Override
	protected @NotNull ItemLike getBaseSeedId(){
		return ModItems.FLAMEFLOWERS_SEED.get();
	}

	@Override
	public boolean canBloom(ServerLevel world, BlockState state){
		long dayTime = world.dayTime() % 24000;
		return dayTime >= 9760L && world.isDay() && !world.isRaining();
	}
}
