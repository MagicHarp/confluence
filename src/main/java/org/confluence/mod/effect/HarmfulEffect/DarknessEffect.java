package org.confluence.mod.effect.HarmfulEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class DarknessEffect extends MobEffect { //黑暗 换皮寻声守卫的黑暗
    public DarknessEffect() {
        super(MobEffectCategory.HARMFUL,0x1C1C1C);
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player && !entity.isSpectator()){
            entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS,1,amplifier,false,false));
        }
    }
}
