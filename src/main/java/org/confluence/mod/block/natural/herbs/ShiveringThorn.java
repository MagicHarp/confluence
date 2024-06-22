package org.confluence.mod.block.natural.herbs;

import net.minecraft.core.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import org.confluence.mod.block.natural.*;
import org.confluence.mod.item.*;
import org.jetbrains.annotations.*;

public class ShiveringThorn extends BaseHerbBlock {

	@Override
	public boolean mayPlaceOn(@NotNull BlockState groundState, @NotNull BlockGetter worldIn, @NotNull BlockPos pos){
		return groundState.is(Blocks.ICE)|| groundState.is(Blocks.SNOW_BLOCK); // TODO: 邪恶/神圣冰雪块
	}

	@Override
	protected @NotNull ItemLike getBaseSeedId(){
		return ModItems.SHIVERINGTHORNS_SEED.get();
	}

	@Override
	public boolean canBloom(ServerLevel world, BlockState state){
		return getAge(state) == MAX_AGE;
	}

	@Override
	public void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom){
		if(getAge(pState) == MAX_AGE - 1){
			pLevel.setBlockAndUpdate(pPos, pState.setValue(AGE, MAX_AGE));
		}
		super.randomTick(pState, pLevel, pPos, pRandom);
	}
}
