package org.confluence.mod.item.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class LowGradeFood {
    public static final FoodProperties W = (new FoodProperties.Builder()).nutrition(4).saturationMod(1.2F).effect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 1.0F)
        .effect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0), 1.0F).alwaysEat().build();

    private static FoodProperties.Builder stew(int p_150384_) {
        return (new FoodProperties.Builder()).nutrition(p_150384_).saturationMod(0.6F);
    }
}