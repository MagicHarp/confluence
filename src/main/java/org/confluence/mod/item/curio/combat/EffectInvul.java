package org.confluence.mod.item.curio.combat;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.util.CuriosUtils;

public interface EffectInvul {
    static boolean isInvul(MobEffect mobEffect, LivingEntity living) {
        if (mobEffect == MobEffects.POISON) {
            return CuriosUtils.hasCurio(living, Poison.class);
        } else if (mobEffect == MobEffects.BLINDNESS) {
            return CuriosUtils.hasCurio(living, Blindness.class);
        } else if (mobEffect == MobEffects.MOVEMENT_SLOWDOWN) {
            return CuriosUtils.hasCurio(living, Slowness.class);
        } else if (mobEffect == MobEffects.WEAKNESS) {
            return CuriosUtils.hasCurio(living, Weakness.class);
        } else if (mobEffect == MobEffects.DIG_SLOWDOWN) {
            return CuriosUtils.hasCurio(living, MiningFatigue.class);
        } else if (mobEffect == MobEffects.CONFUSION) {
            return CuriosUtils.hasCurio(living, Nausea.class);
        } else if (mobEffect == MobEffects.HUNGER) {
            return CuriosUtils.hasCurio(living, Hunger.class);
        } else if (mobEffect == MobEffects.WITHER) {
            return CuriosUtils.hasCurio(living, Wither.class);
        } else if (mobEffect == MobEffects.LEVITATION) {
            return CuriosUtils.hasCurio(living, Levitation.class);
        } else if (mobEffect == MobEffects.DARKNESS) {
            return CuriosUtils.hasCurio(living, Darkness.class);
        }
        return false;
    }

    interface Poison {}

    interface Blindness {}

    interface Slowness {}

    interface Weakness {}

    interface MiningFatigue {}

    interface Nausea {}

    interface Hunger {}

    interface Wither {}

    interface Levitation {}

    interface Darkness {}
}
