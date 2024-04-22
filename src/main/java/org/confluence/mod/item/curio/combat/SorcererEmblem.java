package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;

public class SorcererEmblem extends BaseCurioItem implements IMagicAttack {
    public SorcererEmblem() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public double getMagicBonus() {
        return 0.15;
    }
}
