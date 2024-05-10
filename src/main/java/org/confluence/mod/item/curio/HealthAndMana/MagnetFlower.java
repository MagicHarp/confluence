package org.confluence.mod.item.curio.HealthAndMana;

import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.IRangePickup;

public class MagnetFlower extends ManaFlower implements IRangePickup.Star {
    public MagnetFlower() {
        super(ModRarity.PINK);
    }
}
