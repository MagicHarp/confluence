package org.confluence.mod.item.curio.combat;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import org.confluence.mod.capability.curio.AbilityProvider;

public interface ICriticalHit {
    float getChance();

    default void freshChance(LivingEntity living) {
        living.getCapability(AbilityProvider.ABILITY_CAPABILITY).ifPresent(playerAbility -> {
            playerAbility.freshCriticalChance(living);
        });
    }

    static void apply(CriticalHitEvent event) {
        Player player = event.getEntity();
        player.getCapability(AbilityProvider.ABILITY_CAPABILITY).ifPresent(playerAbility -> {
            if (!event.isVanillaCritical() && player.level().random.nextFloat() < playerAbility.getCriticalChance()) {
                event.setDamageModifier(1.5F);
                event.setResult(Event.Result.ALLOW);
            }
        });
    }
}
