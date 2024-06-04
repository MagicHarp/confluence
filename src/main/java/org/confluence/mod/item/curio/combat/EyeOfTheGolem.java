package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class EyeOfTheGolem extends BaseCurioItem implements ICriticalHit {
    public EyeOfTheGolem() {
        super(ModRarity.LIME);
    }

    @Override
    public double getChance() {
        return 0.1;
    }
}
