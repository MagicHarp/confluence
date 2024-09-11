package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class ArmorBracing extends BaseCurioItem implements EffectInvul.BrokenArmor, EffectInvul.Weakness {
    public ArmorBracing() {
        super(ModRarity.PINK);
    }
}
