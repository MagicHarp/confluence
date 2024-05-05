package org.confluence.mod.item.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import org.confluence.mod.effect.ModEffects;

public class FoodType {
    //低级，2:30
    public static final FoodProperties LOW = new FoodProperties.Builder().nutrition(1).saturationMod(1.5f).fast().alwaysEat()
        .effect(() -> new MobEffectInstance(ModEffects.EXQUISITELY_STUFFED.get(), 3000), 1f).build();
    //中级,5:00
    public static final FoodProperties MEDIUM = new FoodProperties.Builder().nutrition(3).saturationMod(2.5f).fast().alwaysEat()
        .effect(() -> new MobEffectInstance(ModEffects.EXQUISITELY_STUFFED.get(), 6000), 1f).build();
    //中高级,7:30
    public static final FoodProperties SENIOR = new FoodProperties.Builder().nutrition(4).saturationMod(3.5f).fast().meat().alwaysEat()
        .effect(() -> new MobEffectInstance(ModEffects.EXQUISITELY_STUFFED.get(), 9000), 1f).build();
    //高级,10:00
    public static final FoodProperties HIGH = new FoodProperties.Builder().nutrition(6).saturationMod(4.5f).fast().meat().alwaysEat()
        .effect(() -> new MobEffectInstance(ModEffects.EXQUISITELY_STUFFED.get(), 12000), 1f).build();
    //常规肉
    public static final FoodProperties MEAT = new FoodProperties.Builder().nutrition(3).saturationMod(2.5f).fast().meat().alwaysEat()
        .effect(() -> new MobEffectInstance(ModEffects.EXQUISITELY_STUFFED.get(), 3000), 1f).build();
    //泰拉基础鱼
    public static final FoodProperties FISH = new FoodProperties.Builder().nutrition(2).saturationMod(2.5f).fast().alwaysEat()
        .build();
    //金鲤鱼
    public static final FoodProperties GOLDEN_CARP = new FoodProperties.Builder().nutrition(6).saturationMod(6.5f).fast().meat().alwaysEat()
        .effect(() -> new MobEffectInstance(ModEffects.EXQUISITELY_STUFFED.get(), 24000), 1f)
        .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 1.0F)
        .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0), 1.0F)
        .build();
    //重力药水
    public static final FoodProperties GRAVITY_POTION = new FoodProperties.Builder().nutrition(0).saturationMod(0f).fast().alwaysEat()
        .effect(() -> new MobEffectInstance(ModEffects.GRAVITATION.get(), 3600), 1f)
        .build();
    //铁皮药水
    public static final FoodProperties IRON_SKIN_POTION = new FoodProperties.Builder().nutrition(0).saturationMod(0f).fast().alwaysEat()
        .effect(() -> new MobEffectInstance(ModEffects.IRON_SKIN.get(), 9600), 1f)
        .build();
    //怒气药水
    public static final FoodProperties WRATH_POTION = new FoodProperties.Builder().nutrition(0).saturationMod(0f).fast().alwaysEat()
        .effect(() -> new MobEffectInstance(ModEffects.WRATH.get(), 4800), 1f)
        .build();
    //建筑工药水
    public static final FoodProperties BUILDER_POTION = new FoodProperties.Builder().nutrition(0).saturationMod(0f).fast().alwaysEat()
        .effect(() -> new MobEffectInstance(ModEffects.BUILDER.get(), 54000), 1f)
        .build();
    //泰坦药水
    public static final FoodProperties TITAN_POTION= new FoodProperties.Builder().nutrition(0).saturationMod(0f).fast().alwaysEat()
        .effect(() -> new MobEffectInstance(ModEffects.TITAN.get(), 9600), 1f)
        .build();
    //耐力药水
    public static final FoodProperties ENDURANCE_POTION= new FoodProperties.Builder().nutrition(0).saturationMod(0f).fast().alwaysEat()
        .effect(() -> new MobEffectInstance(ModEffects.ENDURANCE.get(), 4800), 1f)
        .build();
    //狱火药水
    public static final FoodProperties INFERNO_POTION= new FoodProperties.Builder().nutrition(0).saturationMod(0f).fast().alwaysEat()
        .effect(() -> new MobEffectInstance(ModEffects.INFERNO.get(), 4800), 1f)
        .build();
    //生命力药水
    public static final FoodProperties LIFEFORCE_POTION= new FoodProperties.Builder().nutrition(0).saturationMod(0f).fast().alwaysEat()
        .effect(() -> new MobEffectInstance(ModEffects.LIFE_FORCE.get(), 9600), 1f)
        .build();
}