package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class NutrientSolution extends BaseCurioItem implements EffectInvul.Weakness, EffectInvul.Hunger {
    public NutrientSolution() {
        super(ModRarity.PINK);
    }
}
