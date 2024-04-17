package org.confluence.mod.item.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import org.confluence.mod.effect.ModEffects;

public class LowGradeFood {
    public static FoodProperties CookedMarshmallow = new FoodProperties.Builder().nutrition(1).saturationMod(1.5f).fast()
        .effect(() -> new MobEffectInstance(ModEffects.EXQUISITELY_STUFFED.get(), 3000), 1f).build();
}