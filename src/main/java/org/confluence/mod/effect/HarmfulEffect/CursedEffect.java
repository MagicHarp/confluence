package org.confluence.mod.effect.HarmfulEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class CursedEffect extends MobEffect {   //诅咒 禁止玩家使用物品
    public CursedEffect() {
        super(MobEffectCategory.HARMFUL, 0x4F4F4F);
    }
}
