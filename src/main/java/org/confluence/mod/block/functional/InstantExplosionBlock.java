package org.confluence.mod.block.functional;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.functional.mechanical.BaseMechanicalBlock;
import org.confluence.mod.datagen.limit.ICubeBottomTop;
import org.jetbrains.annotations.NotNull;

public class InstantExplosionBlock extends BaseMechanicalBlock implements ICubeBottomTop {
    public InstantExplosionBlock() {
        super(Properties.copy(Blocks.TNT));
    }

    @Override
    public void neighborChanged(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Block pNeighborBlock, @NotNull BlockPos pNeighborPos, boolean pMovedByPiston) {
        if (!pLevel.isClientSide) {
            if (pLevel.hasNeighborSignal(pPos)) {
                pLevel.destroyBlock(pPos, false, null);
                pLevel.explode(null, pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, 10.0F, false, Level.ExplosionInteraction.BLOCK);
            }
        }
    }
}
