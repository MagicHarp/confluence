package org.confluence.mod.block.natural.herbs;

import com.lowdragmc.shimmer.client.light.ColorPointLight;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.natural.BaseHerbBlock;
import org.confluence.mod.client.particle.ModParticles;
import org.confluence.mod.item.ModItems;
import org.jetbrains.annotations.NotNull;

public class FlameFlower extends BaseHerbBlock {
    private static final ColorPointLight.Template LIGHT = new ColorPointLight.Template(4, 1, 0.86f, 0, 1);

    public FlameFlower(){
        super(BlockBehaviour.Properties.copy(Blocks.DANDELION).randomTicks().lightLevel(value -> value.getValue(AGE) == MAX_AGE ? 4 : 0));
    }

    @Override
    public boolean mayPlaceOn(@NotNull BlockState groundState, @NotNull BlockGetter worldIn, @NotNull BlockPos pos){
        return groundState.is(ModBlocks.ASH_BLOCK.get()) || groundState.is(ModBlocks.ASH_GRASS_BLOCK.get());
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

    public ColorPointLight.Template getColor(BlockState blockState){
        LIGHT.radius = getAge(blockState) == MAX_AGE ? 4 : 0;
        return LIGHT;
    }

    @Override
    public void animateTick(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom){
        if(getAge(pState) != MAX_AGE) return;
        int r = pRandom.nextInt(60);
        if(r > 10) return;
        Vec3 pos = pPos.getCenter().add(pState.getOffset(pLevel, pPos)).offsetRandom(pRandom, 0.3f);
        pLevel.addParticle(ModParticles.FLAMEFLOWER_BLOOM.get(), pos.x, pos.y, pos.z, 0, 0.3f, 0);
    }
}
