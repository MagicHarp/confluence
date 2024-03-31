package org.confluence.mod.item.curio.combat;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import org.confluence.mod.util.ILivingEntity;

public interface ICriticalHit {
    float getChance();

    default void freshChance(LivingEntity living) {
        ((ILivingEntity) living).c$freshCriticalChance(living);
    }

    static void apply(CriticalHitEvent event) {
        Player player = event.getEntity();
        if (!event.isVanillaCritical() && player.level().random.nextFloat() < ((ILivingEntity) player).c$getCriticalChance()) {
            event.setDamageModifier(1.5F);
            event.setResult(Event.Result.ALLOW);
        }
    }
}
