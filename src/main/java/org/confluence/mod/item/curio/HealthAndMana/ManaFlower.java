package org.confluence.mod.item.curio.HealthAndMana;

import net.minecraft.world.item.Rarity;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;

public class ManaFlower extends BaseCurioItem implements IManaReduce, IAutoGetMana {
    public ManaFlower() {
        super(ModRarity.LIGHT_RED);
    }

    public ManaFlower(Rarity rarity) {
        super(rarity);
    }

    @Override
    public double getManaReduce() {
        return 0.08;
    }
}
