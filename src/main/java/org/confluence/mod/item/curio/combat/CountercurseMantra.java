package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;

public class CountercurseMantra extends BaseCurioItem implements EffectInvul.Silenced, EffectInvul.Cursed {
    public CountercurseMantra() {
        super(ModRarity.PINK);
    }
}
