package org.confluence.mod.block.natural.herbs;

import com.lowdragmc.shimmer.client.light.ColorPointLight;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import org.confluence.mod.block.natural.*;
import org.confluence.mod.item.*;
import org.jetbrains.annotations.*;

public class MoonshineGrass extends BaseHerbBlock {
	private static final ColorPointLight.Template LIGHT = new ColorPointLight.Template(BRIGHTNESS,0,0,1,1);

	public MoonshineGrass(){
		super(BlockBehaviour.Properties.copy(Blocks.DANDELION).randomTicks().lightLevel(value -> value.getValue(AGE) == MAX_AGE ? (int)LIGHT.radius : 0));
	}

	@Override
	public boolean mayPlaceOn(@NotNull BlockState groundState, @NotNull BlockGetter worldIn, @NotNull BlockPos pos){
		return groundState.is(Blocks.GRASS_BLOCK); // TODO: 丛林草
	}

	@Override
	protected @NotNull ItemLike getBaseSeedId(){
		return ModItems.MOONSHINE_GRASS_SEED.get();
	}

	public ColorPointLight.Template getColor(BlockState blockState){
		LIGHT.radius = getAge(blockState) == MAX_AGE ? BRIGHTNESS : 0;
		return LIGHT;
	}

	@Override
	public boolean canBloom(ServerLevel world, BlockState state){
		return world.isNight();
	}
}
