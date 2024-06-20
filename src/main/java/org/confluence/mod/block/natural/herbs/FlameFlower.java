package org.confluence.mod.block.natural.herbs;

import net.minecraft.core.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.Vec3;
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

    @Override
    public void animateTick(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom){
        if(getAge(pState) != MAX_AGE) return;
        Vec3 pos = pPos.getCenter().add(pState.getOffset(pLevel, pPos)).offsetRandom(pRandom, 0.1f).add(0,0.25f,0);
        // TODO: 用自己的粒子，这个效果太难绷了
        pLevel.addParticle(ParticleTypes.SMALL_FLAME,pos.x,pos.y,pos.z,0,0.3f,0);
    }
}
