package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;

public class Bezoar extends BaseCurioItem implements EffectInvulnerable.Poison {
    public Bezoar() {
        super(new Properties().rarity(ModRarity.LIGHT_RED));
    }
}
