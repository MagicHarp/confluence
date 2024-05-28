package org.confluence.mod.item.curio.HealthAndMana;

import org.confluence.mod.item.IRangePickup;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class CelestialMagnet extends BaseCurioItem implements IRangePickup.Star {
    public CelestialMagnet() {
        super(ModRarity.LIGHT_RED);
    }
}
