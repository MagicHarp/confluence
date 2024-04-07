package org.confluence.mod.effect.HarmfulEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class SilencedEffect extends MobEffect { //沉默 禁用使用魔力的物品
    public SilencedEffect() {
        super(MobEffectCategory.HARMFUL, 0xFFFAFA);
    }
}
