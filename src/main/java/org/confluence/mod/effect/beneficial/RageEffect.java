package org.confluence.mod.effect.beneficial;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;


public class RageEffect extends MobEffect {
    public static final String CRIT_UUID = "2BC823DC-FEA6-347E-1B35-5F30CB27845C";

    public RageEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF4500);
    }
}
