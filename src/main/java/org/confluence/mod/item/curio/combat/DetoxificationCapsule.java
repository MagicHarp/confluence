package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class DetoxificationCapsule extends BaseCurioItem implements EffectInvul.Poison, EffectInvul.Wither {
    public DetoxificationCapsule() {
        super(ModRarity.PINK);
    }
}
