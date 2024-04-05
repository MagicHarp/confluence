package org.confluence.mod.effect.BeneficialEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class FeatherFallEffect extends MobEffect {  //羽落 换皮缓降
    public FeatherFallEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xF8F8FF);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player && !entity.isSpectator()) {
            entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 200, amplifier, false, false));
        }
    }
}
