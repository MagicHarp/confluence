package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class Searchlight extends BaseCurioItem implements EffectInvul.Blindness, EffectInvul.Darkness {
    public Searchlight() {
        super(ModRarity.PINK);
    }
}
