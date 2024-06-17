package org.confluence.mod.block.natural.herbs;

import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import org.confluence.mod.block.*;
import org.confluence.mod.block.natural.*;
import org.confluence.mod.command.ConfluenceData;
import org.confluence.mod.item.*;
import org.jetbrains.annotations.*;

public class DeathWeed extends BaseHerbBlock {
	@Override
	public boolean mayPlaceOn(@NotNull BlockState groundState, @NotNull BlockGetter worldIn, @NotNull BlockPos pos){
		return groundState.is(ModBlocks.EBONY_STONE.get()) || groundState.is(ModBlocks.ANOTHER_CRIMSON_STONE.get())
			|| groundState.is(ModBlocks.CORRUPT_GRASS_BLOCK.get()) || groundState.is(ModBlocks.ANOTHER_CRIMSON_GRASS_BLOCK.get());
	}

	@Override
	protected @NotNull ItemLike getBaseSeedId(){
		return ModItems.DEATHWEED_SEED.get();
	}

	@Override
	public boolean canBloom(ServerLevel world, BlockState state){
		return world.isNight() && (world.getMoonPhase() == 0 || ConfluenceData.get(world).getMoonSpecific() == 11);
	}
}
