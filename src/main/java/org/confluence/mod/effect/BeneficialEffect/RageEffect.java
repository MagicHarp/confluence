package org.confluence.mod.effect.BeneficialEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;


public class RageEffect extends MobEffect { //暴怒 暴击率+10%
    public RageEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF4500);
    }
}
