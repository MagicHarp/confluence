package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;

public class ThePlan extends BaseCurioItem implements EffectInvul.Slowness, EffectInvul.Confused {
    public ThePlan() {
        super(ModRarity.PINK);
    }
}
