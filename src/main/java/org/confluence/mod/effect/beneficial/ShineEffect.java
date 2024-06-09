package org.confluence.mod.effect.beneficial;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ShineEffect extends MobEffect {
    public ShineEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x66FF66);
    }

    public static void onAdd(MobEffect mobEffect, LivingEntity living) {

    }

    public static void onRemove(MobEffect mobEffect, LivingEntity living) {

    }
}
