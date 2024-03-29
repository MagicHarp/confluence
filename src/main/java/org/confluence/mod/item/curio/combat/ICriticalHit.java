package org.confluence.mod.item.curio.combat;

import net.minecraft.world.entity.player.Player;
import org.confluence.mod.util.LivingMixin;

public interface ICriticalHit {
    float getChance();

    default void freshChance(Player player) {
        ((LivingMixin) player).c$freshCriticalChance();
    }

    static boolean apply(boolean vanillaCritical, Player player) {
        return !vanillaCritical && player.level().random.nextFloat() < ((LivingMixin) player).c$getCriticalChance();
    }
}
