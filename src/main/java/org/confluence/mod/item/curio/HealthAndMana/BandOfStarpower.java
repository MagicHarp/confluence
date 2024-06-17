package org.confluence.mod.item.curio.HealthAndMana;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Rarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class BandOfStarpower extends BaseCurioItem {
    public BandOfStarpower() {
        super(ModRarity.BLUE);
    }

    public BandOfStarpower(Rarity rarity) {
        super(rarity);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.band_of_starpower.info"),
            Component.translatable("item.confluence.band_of_starpower.info2")
        };
    }
}
