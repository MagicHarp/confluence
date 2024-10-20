package org.confluence.mod.common.event.game.entity;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.*;
import org.confluence.mod.Confluence;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = Confluence.MODID)
public final class LivingEntityEvents {
    @SubscribeEvent
    public static void livingDamage$Post(LivingDamageEvent.Post event) {

    }

    @SubscribeEvent
    public static void livingDeath(LivingDeathEvent event) {

    }

    @SubscribeEvent
    public static void livingChangeTarget(LivingChangeTargetEvent event) {

    }

    @SubscribeEvent
    public static void livingHeal(LivingHealEvent event) {

    }

    @SubscribeEvent
    public static void livingIncomingDamage(LivingIncomingDamageEvent event) { // livingDamage

    }

    @SubscribeEvent
    public static void livingBreathe(LivingBreatheEvent event) {

    }

    @SubscribeEvent
    public static void finalizeSpawn(FinalizeSpawnEvent event) {

    }

    @SubscribeEvent
    public static void effectApplicable(MobEffectEvent.Applicable event) {

    }

    @SubscribeEvent
    public static void effectAdded(MobEffectEvent.Added event) {

    }

    @SubscribeEvent
    public static void effectExpired(MobEffectEvent.Expired event) {

    }

    @SubscribeEvent
    public static void effectRemove(MobEffectEvent.Remove event) {

    }

    @SubscribeEvent
    public static void livingEquipmentChange(LivingEquipmentChangeEvent event) {

    }

    @SubscribeEvent
    public static void livingEntityUseItem$tick(LivingEntityUseItemEvent.Tick event) {

    }
}
