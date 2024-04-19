package org.confluence.mod.item.curio.HealthAndMana;

import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.combat.IAggroAttach;

public class ArcaneFlower extends BaseCurioItem implements IManaReduce, IAggroAttach {
    public ArcaneFlower() {
        super(ModRarity.PINK);
    }

    @Override
    public double getManaReduce() {
        return 0.08;
    }

    @Override
    public int getAggro() {
        return -400;
    }
}
