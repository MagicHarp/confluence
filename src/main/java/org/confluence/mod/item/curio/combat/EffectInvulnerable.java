package org.confluence.mod.item.curio.combat;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.util.CuriosUtils;

public interface EffectInvulnerable {
    static boolean apply(MobEffect mobEffect, LivingEntity living) {
        if (mobEffect == MobEffects.POISON) {
            return CuriosUtils.hasCurio(living, Poison.class);
        } else if (mobEffect == MobEffects.BLINDNESS) {
            return CuriosUtils.hasCurio(living, Blindness.class);
        }
        return false;
    }

    interface Poison {
    }

    interface Blindness {
    }
}
