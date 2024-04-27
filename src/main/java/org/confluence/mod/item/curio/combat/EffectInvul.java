package org.confluence.mod.item.curio.combat;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.effect.ModEffects;
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
        } else if (mobEffect == ModEffects.BLEEDING.get()) {
            return CuriosUtils.hasCurio(living, Bleeding.class);
        } else if (mobEffect == ModEffects.BROKEN_ARMOR.get()) {
            return CuriosUtils.hasCurio(living, BrokenArmor.class);
        } else if (mobEffect == ModEffects.CONFUSED.get()) {
            return CuriosUtils.hasCurio(living, Confused.class);
        } else if (mobEffect == ModEffects.CURSED.get()) {
            return CuriosUtils.hasCurio(living, Cursed.class);
        } else if (mobEffect == ModEffects.SILENCED.get()) {
            return CuriosUtils.hasCurio(living, Silenced.class);
        } else if (mobEffect == ModEffects.STONED.get()) {
            return CuriosUtils.hasCurio(living, Stoned.class);
        }
        return false;
    }

    interface Poison {}

    interface Blindness {}

    interface Slowness {}

    interface Weakness {}

    interface Bleeding {}

    interface BrokenArmor {}

    interface Confused {}

    interface Cursed {}

    interface Silenced {}

    interface Stoned {}
}
