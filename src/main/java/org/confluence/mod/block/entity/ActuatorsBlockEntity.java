package org.confluence.mod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.ConfluenceBlocks;

public class ActuatorsBlockEntity extends BlockEntity {
    public ActuatorsBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ConfluenceBlocks.ACTUATORS_ENTITY.get(), blockPos, blockState);
    }
}
