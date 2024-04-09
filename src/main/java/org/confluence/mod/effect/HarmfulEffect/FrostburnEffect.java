package org.confluence.mod.effect.HarmfulEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.confluence.mod.effect.ModEffects;

public class FrostburnEffect extends MobEffect {    //霜冻：缓慢损失生命 每秒损失1.5点生命
    public FrostburnEffect() {
        super(MobEffectCategory.HARMFUL, 0xBBFFFF);
    }

    public static void onAdd(LivingEntity entity) {
        if (entity instanceof Player && entity.getHealth() > 1.0F) {
            entity.hurt(entity.damageSources().magic(), 1.5F);
        }
    }

    public static void onRemove(LivingEntity entity) {
        if (entity instanceof Player && entity.hasEffect(new MobEffectInstance(ModEffects.FROSTBURN.get()).getEffect())) {
            entity.removeEffect(new MobEffectInstance(ModEffects.FROSTBURN.get()).getEffect());
        }
    }
}
