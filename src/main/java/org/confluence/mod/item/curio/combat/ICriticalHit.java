package org.confluence.mod.item.curio.combat;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.util.CuriosUtils;

public interface ICriticalHit {
    double getChance();

    static void apply(CriticalHitEvent event) {
        Player player = event.getEntity();
        player.getCapability(AbilityProvider.CAPABILITY).ifPresent(playerAbility -> {
            if (!event.isVanillaCritical()) {
                double chance = playerAbility.getCriticalChance();
                if (player.level().getDayTime() % 24000 > 12000) {
                    if (CuriosUtils.hasCurio(player, CurioItems.MOON_STONE.get())) chance += 0.02;
                } else {
                    if (CuriosUtils.hasCurio(player, CurioItems.SUN_STONE.get())) chance += 0.02;
                }
                if (player.level().random.nextFloat() < chance) {
                    event.setDamageModifier(1.5F);
                    event.setResult(Event.Result.ALLOW);
                }
            }
        });
    }
}
