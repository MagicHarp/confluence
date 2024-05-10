package org.confluence.mod.item.curio.HealthAndMana;

import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.IRangePickup;
import org.confluence.mod.item.curio.combat.IMagicAttack;

public class CelestialEmblem extends BaseCurioItem implements IMagicAttack, IRangePickup.Star {
    public CelestialEmblem() {
        super(ModRarity.PINK);
    }

    @Override
    public double getMagicBonus() {
        return 0.15;
    }
}
