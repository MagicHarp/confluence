package org.confluence.mod.effect.HarmfulEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class FrostburnEffect extends MobEffect {    //霜冻：缓慢损失生命 每秒损失1.5点生命
    public FrostburnEffect() {
        super(MobEffectCategory.HARMFUL, 0xBBFFFF);
    }

    public static void onAdd(LivingEntity entity) {
        if (entity instanceof Player && entity.getHealth() > 1.0F) {
            entity.hurt(entity.damageSources().magic(), 1.5F);
        }
    }
}
