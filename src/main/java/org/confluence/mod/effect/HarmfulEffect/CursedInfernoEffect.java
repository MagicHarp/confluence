package org.confluence.mod.effect.HarmfulEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.confluence.mod.effect.ModEffects;

public class CursedInfernoEffect extends MobEffect {    //诅咒火 缓慢失去生命 每秒损失2点生命
    public CursedInfernoEffect() {
        super(MobEffectCategory.HARMFUL, 0x228B22);
    }

    public static void onAdd(LivingEntity entity) {
        if (entity instanceof Player && entity.getHealth() > 1.0F) {
            entity.hurt(entity.damageSources().magic(), 2);
        }
    }

    public static void onRemove(LivingEntity entity){
        if (entity instanceof Player && entity.hasEffect(new MobEffectInstance(ModEffects.CURSED_INFERNO.get()).getEffect())){
            entity.removeEffect(new MobEffectInstance(ModEffects.CURSED_INFERNO.get()).getEffect());
        }
    }
}
