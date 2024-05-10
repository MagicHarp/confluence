package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class CrispyHoneyBlock extends Block {
    public CrispyHoneyBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(1.2F));
    }

    public void playerDestroy(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull BlockPos pPos, @NotNull BlockState pState, @Nullable BlockEntity pTe, @NotNull ItemStack pStack) {
        super.playerDestroy(pLevel, pPlayer, pPos, pState, pTe, pStack);
        if (pLevel.random.nextBoolean()) {
            pLevel.setBlockAndUpdate(pPos, Blocks.LAVA.defaultBlockState());
        }
    }

    @Override
    public void stepOn(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, Entity pEntity) {
        pEntity.setSecondsOnFire(5);
    }
}
