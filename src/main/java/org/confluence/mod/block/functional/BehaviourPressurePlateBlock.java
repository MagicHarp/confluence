package org.confluence.mod.block.functional;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.confluence.mod.block.functional.network.INetworkBlock;
import org.confluence.mod.block.functional.network.INetworkEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.POWERED;

public class BehaviourPressurePlateBlock extends BasePressurePlateBlock implements EntityBlock, INetworkBlock {
    public static final Behaviour PLAYER = new Behaviour() {
        @Override
        protected int getSignalStrength(Level level, BlockPos blockPos) {
            for (Player player : level.players()) {
                if (TOUCH_AABB.contains(player.getX(), player.getY(), player.getZ())) {
                    return 15;
                }
            }
            return 0;
        }
    };
    private final Behaviour behaviour;

    public BehaviourPressurePlateBlock(Behaviour behaviour, Properties pProperties, BlockSetType pType) {
        super(pProperties, pType);
        this.behaviour = behaviour;
        registerDefaultState(stateDefinition.any().setValue(POWERED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(POWERED);
    }

    @Override
    public void onRemove(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pNewState, boolean pMovedByPiston) {
        onNodeRemove(pState, pLevel, pPos, pNewState);
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new AbstractMechanicalBlock.Entity(blockPos, blockState);
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        int i = getSignalForState(pState);
        int j = getSignalStrength(pLevel, pPos);
        if (i > 0 && i != j) {
            execute(pState, pLevel, pPos, j > 0);
        }
        super.tick(pState, pLevel, pPos, pRandom);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        super.entityInside(pState, pLevel, pPos, pEntity);
        if (pLevel instanceof ServerLevel serverLevel) {
            int i = getSignalForState(pState);
            if (i == 0) {
                execute(pState, serverLevel, pPos, true);
            }
        }
    }

    @Override
    public void onExecute(BlockState pState, ServerLevel pLevel, BlockPos pPos, int pColor, INetworkEntity pEntity) {
        behaviour.onExecute(pState, pLevel, pPos, pColor, pEntity);
    }

    @Override
    public void onUnExecute(BlockState pState, ServerLevel pLevel, BlockPos pPos, int pColor, INetworkEntity pEntity) {
        behaviour.onUnExecute(pState, pLevel, pPos, pColor, pEntity);
    }

    @Override
    protected int getSignalStrength(@NotNull Level level, @NotNull BlockPos blockPos) {
        return behaviour.getSignalStrength(level, blockPos);
    }

    @Override
    protected int getSignalForState(@NotNull BlockState blockState) {
        return behaviour.getSignalForState(blockState);
    }

    @Override
    protected @NotNull BlockState setSignalForState(@NotNull BlockState blockState, int i) {
        return behaviour.setSignalForState(blockState, i);
    }

    public static abstract class Behaviour {
        public void onExecute(BlockState pState, ServerLevel pLevel, BlockPos pPos, int pColor, INetworkEntity pEntity) {}

        public void onUnExecute(BlockState pState, ServerLevel pLevel, BlockPos pPos, int pColor, INetworkEntity pEntity) {}

        protected int getSignalForState(BlockState blockState) {
            return blockState.getValue(POWERED) ? 15 : 0;
        }

        protected BlockState setSignalForState(BlockState blockState, int strength) {
            return blockState.setValue(POWERED, strength > 0);
        }

        protected abstract int getSignalStrength(Level level, BlockPos blockPos);
    }
}
