package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.confluence.mod.datagen.limit.ICubeBottomTop;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.piston.MovingPistonBlock.FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.TRIGGERED;

public class FloatingWheatBaleBlock extends Block implements ICubeBottomTop {
    public FloatingWheatBaleBlock() {
        super(Properties.copy(Blocks.HAY_BLOCK).sound(SoundType.GRASS));
    }

    public void fallOn(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, Entity entity, float fallDistance) {
        if (entity.isSuppressingBounce()) {
            super.fallOn(level, state, pos, entity, fallDistance);
        }
    }
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(DirectionalBlock.FACING, pContext.getNearestLookingDirection().getOpposite());
    }

    public @NotNull BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(DirectionalBlock.FACING, pRotation.rotate(pState.getValue(DirectionalBlock.FACING)));
    }

    public @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(DirectionalBlock.FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(DirectionalBlock.FACING, TRIGGERED);
    }
}
