package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.curio.BaseCurioItem;

public class EyeOfTheGolem extends BaseCurioItem implements ICriticalHit {
    @Override
    public float getChance() {
        return 0.1F;
    }
}
