package org.confluence.mod.item.curio.HealthAndMana;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.IRangePickup;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class CelestialMagnet extends BaseCurioItem implements IRangePickup.Star {
    public CelestialMagnet() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.celestial_magnet.info"),
            Component.translatable("item.confluence.celestial_magnet.info2"),
            Component.translatable("item.confluence.celestial_magnet.info3"),
            Component.translatable("item.confluence.celestial_magnet.info4")
        };
    }
}
