package org.confluence.mod.effect.beneficial;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.effect.ModEffects;

public class MagicPowerEffect extends MobEffect {
    public MagicPowerEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xCC00CC);
    }

    public static float apply(LivingEntity living, float amount) {
        if (living.hasEffect(ModEffects.MAGIC_POWER.get())) {
            return amount * 1.2F;
        }
        return amount;
    }
}
