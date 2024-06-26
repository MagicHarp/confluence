package org.confluence.mod.block.functional;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.functional.mechanical.AbstractMechanicalBlock;
import org.confluence.mod.datagen.limit.ICubeBottomTop;
import org.jetbrains.annotations.NotNull;

public class InstantExplosionBlock extends AbstractMechanicalBlock implements ICubeBottomTop {
    public InstantExplosionBlock() {
        super(Properties.copy(Blocks.TNT));
    }

    @Override
    public void neighborChanged(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Block pNeighborBlock, @NotNull BlockPos pNeighborPos, boolean pMovedByPiston) {
        if (!pLevel.isClientSide && pLevel.hasNeighborSignal(pPos)) {
            execute(pState, (ServerLevel) pLevel, pPos, true);
        }
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        level.explode(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10.0F, false, Level.ExplosionInteraction.BLOCK);
    }

    @Override
    public void onExecute(BlockState pState, ServerLevel pLevel, BlockPos pPos) {
        pLevel.removeBlock(pPos, false);
        pLevel.explode(null, pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, 10.0F, false, Level.ExplosionInteraction.BLOCK);
    }
}
