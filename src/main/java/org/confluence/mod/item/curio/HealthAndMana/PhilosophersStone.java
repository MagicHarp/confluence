package org.confluence.mod.item.curio.HealthAndMana;

import net.minecraft.world.item.Rarity;
import org.confluence.mod.datagen.limit.CustomName;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class PhilosophersStone extends BaseCurioItem implements CustomName {
    public PhilosophersStone() {
        super(ModRarity.LIGHT_RED);
    }

    public PhilosophersStone(Rarity rarity) {
        super(rarity);
    }

    @Override
    public String getGenName() {
        return "Philosopher's Stone";
    }
}
