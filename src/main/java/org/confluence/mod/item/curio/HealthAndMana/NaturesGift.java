package org.confluence.mod.item.curio.HealthAndMana;

import org.confluence.mod.datagen.limit.CustomName;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;

public class NaturesGift extends BaseCurioItem implements IManaReduce, CustomName {
    public NaturesGift() {
        super(ModRarity.ORANGE);
    }

    @Override
    public double getManaReduce() {
        return 0.06;
    }

    @Override
    public String getName() {
        return "Nature's Gift";
    }
}
