package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.magic.IMagicAttack;

public class SorcererEmblem extends BaseCurioItem implements IMagicAttack {
    public SorcererEmblem() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public double getBonus() {
        return 0.15;
    }
}
