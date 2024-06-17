package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;

public class SorcererEmblem extends BaseCurioItem implements IMagicAttack {
    public SorcererEmblem() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public double getMagicBonus() {
        return ModConfigs.SORCERER_EMBLEM_MAGIC_BONUS.get();
    }
}
