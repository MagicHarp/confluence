package org.confluence.mod.item.curio.HealthAndMana;

import net.minecraft.world.item.Rarity;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;

public class BandOfStarpower extends BaseCurioItem {
    public BandOfStarpower() {
        super(ModRarity.BLUE);
    }

    public BandOfStarpower(Rarity rarity) {
        super(rarity);
    }
}
