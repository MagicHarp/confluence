package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;

public class ReflectiveShades extends BaseCurioItem implements EffectInvul.Blindness, EffectInvul.Stoned {
    public ReflectiveShades() {
        super(ModRarity.PINK);
    }
}
