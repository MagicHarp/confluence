package org.confluence.mod.block.natural;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.confluence.mod.datagen.limit.CustomModel;

public class AshBlock extends Block implements CustomModel {
    public AshBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.STONE)
            .strength(1.0F, 1.0F)
            .sound(SoundType.SAND)
        );
    }
}