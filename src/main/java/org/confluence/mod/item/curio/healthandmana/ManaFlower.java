package org.confluence.mod.item.curio.healthandmana;

import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;

public class ManaFlower extends BaseCurioItem implements IManaReduce, IAutoGetMana {
    public ManaFlower() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public double getManaReduce() {
        return 0.08;
    }
}
