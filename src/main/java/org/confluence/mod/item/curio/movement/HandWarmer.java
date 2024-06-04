package org.confluence.mod.item.curio.movement;

import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.combat.EffectInvul;
import org.confluence.mod.misc.ModRarity;

public class HandWarmer extends BaseCurioItem implements EffectInvul.FrostBurn {
    public HandWarmer() {
        super(ModRarity.GREEN);
    }
}
