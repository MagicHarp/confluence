package org.confluence.mod.effect.BeneficialEffect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ThornsEffect extends MobEffect {   //荆棘 给予伤害来源反伤
    static DamageSource damageSource;

    public ThornsEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00FF00);
    }

    public static void onAdd(LivingEntity entity, float ThornHurt) {
        if (entity instanceof Player) {
            onThorn(entity, ThornHurt);
        }
    }

    private static void onThorn(LivingEntity entity, float ThornHurt) {
        if (damageSource != null) {
            entity.hurt(damageSource, ThornHurt);
        }
    }
}
