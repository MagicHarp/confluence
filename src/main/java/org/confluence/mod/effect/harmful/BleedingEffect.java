package org.confluence.mod.effect.harmful;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import org.confluence.mod.effect.ModEffects;

public class BleedingEffect extends MobEffect { //流血 不能自然恢复生命
    public BleedingEffect() {
        super(MobEffectCategory.HARMFUL, 0xA52A2A);
    }

    public static void apply(LivingEntity entity, LivingHealEvent event) {
        if (entity.hasEffect(ModEffects.BLEEDING.get())) {
            event.setCanceled(true);
        }
    }
}
