package org.confluence.mod.effect.HarmfulEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class CursedInfernoEffect extends MobEffect {    //诅咒火 缓慢失去生命 每秒损失2点生命
    public CursedInfernoEffect() {
        super(MobEffectCategory.HARMFUL, 0x228B22);
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        living.hurt(living.damageSources().magic(), 2);
    }

    @Override
    public boolean isDurationEffectTick(int tick, int amplifier) {
        return tick % 20 == 0;
    }
}
