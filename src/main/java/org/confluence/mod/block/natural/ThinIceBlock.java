package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.util.CuriosUtils;
import org.jetbrains.annotations.NotNull;

public class ThinIceBlock extends IceBlock {
    public ThinIceBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE));
    }

    @Override
    public void fallOn(@NotNull Level level, @NotNull BlockState blockState, @NotNull BlockPos blockPos, @NotNull Entity entity, float distance) {
        if (!level.isClientSide /*&& distance > 3*/ && entity instanceof LivingEntity living && CuriosUtils.noSameCurio(living, CurioItems.ICE_SKATES.get())) {
            level.destroyBlock(blockPos, true, entity);
            Confluence.LOGGER.info("fall distance: " + distance);
        }
        super.fallOn(level, blockState, blockPos, entity, distance);
    }

    public interface ISafeIce {
    }
}
