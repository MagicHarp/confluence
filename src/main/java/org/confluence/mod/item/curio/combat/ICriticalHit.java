package org.confluence.mod.item.curio.combat;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.confluence.mod.util.ILivingEntity;

public interface ICriticalHit {
    float getChance();

    default void freshChance(LivingEntity living) {
        ((ILivingEntity) living).c$freshCriticalChance();
    }

    static boolean apply(boolean vanillaCritical, Player player) {
        return !vanillaCritical && player.level().random.nextFloat() < ((ILivingEntity) player).c$getCriticalChance();
    }
}
