package org.confluence.mod.effect.harmful;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class BloodButcheredEffect extends MobEffect {

    public BloodButcheredEffect() {
        super(MobEffectCategory.HARMFUL, 0xFF0000);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        float damage;

        switch (pAmplifier) {
            case 2:
                damage = 2F;
                break;
            case 3:
                damage = 4F;
                break;
            case 4:
                damage = 6F;
                break;
            case 5:
                damage = 8F;
                break;
            default:
                damage = 10F;
                break;
        }

        pLivingEntity.hurt(pLivingEntity.level().damageSources().magic(), damage);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}