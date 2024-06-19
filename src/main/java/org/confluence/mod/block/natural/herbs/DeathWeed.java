package org.confluence.mod.block.natural.herbs;

import com.lowdragmc.shimmer.client.light.ColorPointLight;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.natural.BaseHerbBlock;
import org.confluence.mod.command.ConfluenceData;
import org.confluence.mod.item.ModItems;
import org.jetbrains.annotations.NotNull;

public class DeathWeed extends BaseHerbBlock {
	private static final ColorPointLight.Template LIGHT = new ColorPointLight.Template(BRIGHTNESS, 1, 0, 1, 1);


	public DeathWeed(){
		super(BlockBehaviour.Properties.copy(Blocks.DANDELION).randomTicks().lightLevel(value -> value.getValue(AGE) == MAX_AGE ? (int)LIGHT.radius : 0));
	}

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

	public ColorPointLight.Template getColor(BlockState blockState){
		LIGHT.radius = getAge(blockState) == MAX_AGE ? BRIGHTNESS : 0;
		return LIGHT;
	}
}
