package org.confluence.mod.block.natural;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import org.confluence.mod.block.natural.spreadable.ISpreadable;

public class SlushBlock extends SandBlock  {
    public SlushBlock(int color,Properties properties) {
        super(color,properties.strength(0.7F).sound(SoundType.GRAVEL));
    }
}