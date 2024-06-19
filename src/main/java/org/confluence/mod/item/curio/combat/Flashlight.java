package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class Flashlight extends BaseCurioItem implements EffectInvul.Darkness {
    public Flashlight() {
        super(ModRarity.LIGHT_RED);
    }
}
