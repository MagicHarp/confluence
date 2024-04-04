package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;

public class CrossNecklace extends BaseCurioItem implements IInvulnerableTime {
    public CrossNecklace() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public int getTime() {
        return 40;
    }
}
