package org.confluence.mod.block.natural;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SoundType;
import org.confluence.mod.block.natural.spreadable.ISpreadable;

public class SlushBlock extends FallingBlock  {
    public SlushBlock(int color) {
        super(Properties.copy(Blocks.GRAVEL)
            .strength(1.0F, 1.0F)
            .sound(SoundType.GRAVEL)
        );
    }
}