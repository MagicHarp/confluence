package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.ILavaImmune;

public class ObsidianSkullRose extends BaseCurioItem implements IFireImmune, ILavaImmune {
    public ObsidianSkullRose() {
        super(ModRarity.PINK);
    }
}
