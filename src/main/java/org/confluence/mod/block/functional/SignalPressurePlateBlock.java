package org.confluence.mod.block.functional;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.confluence.mod.block.functional.network.INetworkBlock;
import org.confluence.mod.block.functional.network.INetworkEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SignalPressurePlateBlock extends PressurePlateBlock implements EntityBlock, INetworkBlock {
    public SignalPressurePlateBlock(Sensitivity pSensitivity, Properties pProperties, BlockSetType pType) {
        super(pSensitivity, pProperties, pType);
    }

    @Override
    public void onRemove(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pNewState, boolean pMovedByPiston) {
        onNodeRemove(pState, pLevel, pPos, pNewState);
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public void tick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        int i = getSignalForState(pState);
        int j = getSignalStrength(pLevel, pPos);
        if (i > 0 && i != j) {
            execute(pState, pLevel, pPos, j > 0);
        }
        super.tick(pState, pLevel, pPos, pRandom);
    }

    @Override
    public void entityInside(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Entity pEntity) {
        super.entityInside(pState, pLevel, pPos, pEntity);
        if (pLevel instanceof ServerLevel serverLevel) {
            int i = getSignalForState(pState);
            if (i == 0) {
                execute(pState, serverLevel, pPos, true);
            }
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new AbstractMechanicalBlock.Entity(blockPos, blockState);
    }

    @Override
    public void onExecute(BlockState pState, ServerLevel pLevel, BlockPos pPos, int pColor, INetworkEntity pEntity) {}
}
