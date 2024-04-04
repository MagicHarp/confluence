package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;

public class EyeOfTheGolem extends BaseCurioItem implements ICriticalHit {
    public EyeOfTheGolem() {
        super(ModRarity.LIME);
    }

    @Override
    public float getChance() {
        return 0.1F;
    }
}
