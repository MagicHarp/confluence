package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class AdhesiveBandage extends BaseCurioItem implements EffectInvul.Bleeding {
    public AdhesiveBandage() {
        super(ModRarity.LIGHT_RED);
    }
}
