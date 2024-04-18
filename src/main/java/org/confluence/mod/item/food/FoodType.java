package org.confluence.mod.item.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import org.confluence.mod.effect.ModEffects;

public class FoodType {
    //低级
    public static final FoodProperties LOW = new FoodProperties.Builder().nutrition(1).saturationMod(1.5f).fast()
        .effect(() -> new MobEffectInstance(ModEffects.EXQUISITELY_STUFFED.get(), 3000), 1f).build();
    //中级
    public static final FoodProperties MEDIUM = new FoodProperties.Builder().nutrition(2).saturationMod(1.5f).fast()
        .effect(() -> new MobEffectInstance(ModEffects.EXQUISITELY_STUFFED.get(), 6000), 1f).build();
    //高级
    public static final FoodProperties HIGH = new FoodProperties.Builder().nutrition(2).saturationMod(1.5f).fast().meat()
        .effect(() -> new MobEffectInstance(ModEffects.EXQUISITELY_STUFFED.get(), 12000), 1f).build();
    //泰拉基础鱼
    public static final FoodProperties FISH = new FoodProperties.Builder().nutrition(2).saturationMod(2.5f).fast()
        .build();
    //金鲤鱼
    public static final FoodProperties GOLDEN_CARP = new FoodProperties.Builder().nutrition(6).saturationMod(2.5f).fast().meat()
        .effect(() -> new MobEffectInstance(ModEffects.EXQUISITELY_STUFFED.get(), 12000), 1f)
        .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 1.0F)
        .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0), 1.0F)
        .build();
}