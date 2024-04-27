package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;

public class Blindfold extends BaseCurioItem implements EffectInvul.Blindness {
    public Blindfold() {
        super(ModRarity.LIGHT_RED);
    }
}
