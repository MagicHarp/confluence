package org.confluence.mod.mixin.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.natural.BaseHerbBlock;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AirBlock.class)
public abstract class AirBlockMixin extends Block {
    private AirBlockMixin(Properties pProperties){
        super(pProperties);
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState pState){
        return true;
    }

    @Override
    public void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom){
        if(this == Blocks.VOID_AIR) return;
        if(pRandom.nextInt(1000) < 1){
            BlockPos groundPos = pPos.below();
            BlockState groundState = pLevel.getBlockState(groundPos);
            BaseHerbBlock.growNewHerb(groundState,groundPos,pLevel);
        }
    }
}
