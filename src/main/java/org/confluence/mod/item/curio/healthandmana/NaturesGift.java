package org.confluence.mod.item.curio.healthandmana;

import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;

public class NaturesGift extends BaseCurioItem implements IManaReduce {
    public NaturesGift() {
        super(ModRarity.ORANGE);
    }

    @Override
    public double getManaReduce() {
        return 0.06;
    }
}
