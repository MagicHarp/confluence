package org.confluence.mod.block.natural;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import org.confluence.mod.datagen.limit.CustomModel;

public class DesertFossilBlock extends Block implements CustomModel {
    public DesertFossilBlock() {
        super(Properties.copy(Blocks.STONE)
            .strength(1.5F, 1.0F)
        );
    }
}