package org.confluence.mod.item.curio.movement;

import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.ILavaImmune;

public class LavaCharm extends BaseCurioItem implements ILavaImmune {
    public LavaCharm() {
        super(ModRarity.ORANGE);
    }
}
