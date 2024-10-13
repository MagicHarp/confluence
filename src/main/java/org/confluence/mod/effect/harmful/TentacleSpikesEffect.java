package org.confluence.mod.effect.harmful;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class TentacleSpikesEffect extends MobEffect {
    private static int tickCount = 0;

    public TentacleSpikesEffect() {
        super(MobEffectCategory.HARMFUL, 0xFF0000);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) {
        float damage;

        if (tickCount % 35 == 0){
            tickCount = 0;
            damage = pAmplifier + 2.0F;
            pLivingEntity.hurt(pLivingEntity.level().damageSources().magic(), damage);
        }

        ++tickCount;

    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}