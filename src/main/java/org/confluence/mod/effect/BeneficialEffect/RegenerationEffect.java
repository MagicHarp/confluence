package org.confluence.mod.effect.BeneficialEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class RegenerationEffect extends MobEffect { //再生  加生命恢复
    public RegenerationEffect() {
        super(MobEffectCategory.BENEFICIAL,0xFFC0CB);
    }
    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        if (entity instanceof Player && !entity.isSpectator()){
            entity.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST,1,amplifier,false,false));
        }
    }
}
