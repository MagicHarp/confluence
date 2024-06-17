package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;

public class CrossNecklace extends BaseCurioItem implements IInvulnerableTime {
    public CrossNecklace() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public int getTime() {
        return ModConfigs.CROSS_NECKLACE_INVULNERABLE_TIME.get();
    }
}
