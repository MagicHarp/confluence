package org.confluence.mod.block.natural.herbs;

import com.lowdragmc.shimmer.client.light.ColorPointLight;
import net.minecraft.core.*;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.*;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.block.natural.*;
import org.confluence.mod.item.*;
import org.jetbrains.annotations.*;
import org.joml.Vector3f;

public class MoonshineGrass extends BaseHerbBlock {
	private static final ColorPointLight.Template LIGHT = new ColorPointLight.Template(BRIGHTNESS,0,0,1,1);
	private static final IntegerProperty PROP_LIGHT = IntegerProperty.create("level", BRIGHTNESS, 5);


	public MoonshineGrass(){
		super(BlockBehaviour.Properties.copy(Blocks.DANDELION).randomTicks().lightLevel(value -> value.getValue(AGE) == MAX_AGE ? value.getValue(PROP_LIGHT) : 0));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
		super.createBlockStateDefinition(pBuilder);
		pBuilder.add(PROP_LIGHT);
	}


	@Override
	protected @NotNull ItemLike getBaseSeedId(){
		return ModItems.MOONSHINE_GRASS_SEED.get();
	}

	public ColorPointLight.Template getColor(BlockState blockState){
		LIGHT.radius = getAge(blockState) == MAX_AGE ? blockState.getValue(PROP_LIGHT) : 0;
		return LIGHT;
	}

	@Override
	public void animateTick(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom){
		if(getAge(pState) != MAX_AGE) return;
		int r = pRandom.nextInt(200);
		int brightness;
		if(r < 10){
			pLevel.setBlockAndUpdate(pPos, pState.setValue(PROP_LIGHT, 5));
			Vec3 center = pPos.getCenter().offsetRandom(pRandom,0.6f);
			pLevel.addParticle(new DustParticleOptions(new Vector3f(0, 0.7f, 1), 1), center.x, center.y / 2, center.z, 10,10,10);
			center = pPos.getCenter().offsetRandom(pRandom,0.6f);
			pLevel.addParticle(new DustParticleOptions(new Vector3f(0, 0.7f, 1), 1), center.x, center.y / 2, center.z, 10,10,10);
		}else if(r < 160 && (brightness = pState.getValue(PROP_LIGHT)) > BRIGHTNESS){
			pLevel.setBlockAndUpdate(pPos, pState.setValue(PROP_LIGHT, brightness - 1));
		}
	}


	@Override
	public boolean canBloom(ServerLevel world, BlockState state){
		return world.isNight();
	}
}
