package org.confluence.mod.effect.neutral;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import org.confluence.mod.effect.ModEffects;

public class LoveEffect extends MobEffect {
    public LoveEffect() {
        super(MobEffectCategory.NEUTRAL, 0xEE0000);
    }

    public static void onAdd(MobEffect mobEffect, LivingEntity living, Entity entity) {
        if (mobEffect == ModEffects.LOVE.get() && living instanceof Animal animal) {
            animal.setInLove(entity instanceof Player player ? player : null);
        }
    }
}
