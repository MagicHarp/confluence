package org.confluence.mod.mixin.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.natural.BaseHerbBlock;
import org.confluence.mod.mixin.accessor.SpreadingSnowyDirtBlockAccessor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GrassBlock.class)
public class GrassBlockMixin extends SpreadingSnowyDirtBlock{
    private GrassBlockMixin(Properties pProperties){
        super(pProperties);
    }

    @Override
    public void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom){
        super.randomTick(pState, pLevel, pPos, pRandom);
        int r = pRandom.nextInt(1000);
        if(r < 1 && SpreadingSnowyDirtBlockAccessor.callCanBeGrass(pState, pLevel, pPos)){
            BaseHerbBlock.growNewHerb(pState,pPos,pLevel);
        }
    }
}
