package org.confluence.mod.common.event.game.entity;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import org.confluence.mod.common.init.ModAttributes;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class RegisterEntityEvent {

    @SubscribeEvent
    public static void attributeCreate(EntityAttributeCreationEvent event) {

    }

    @SubscribeEvent
    public static void entityAttributeModification(EntityAttributeModificationEvent event) {
        ModAttributes.readJsonConfig();
        ModAttributes.registerAttribute(ModAttributes.CRIT_CHANCE, event::add);
        ModAttributes.registerAttribute(ModAttributes.RANGED_VELOCITY, event::add);
        ModAttributes.registerAttribute(ModAttributes.RANGED_DAMAGE, event::add);
        ModAttributes.registerAttribute(ModAttributes.DODGE_CHANCE, event::add);
        ModAttributes.registerAttribute(ModAttributes.AGGRO, event::add);
        ModAttributes.registerAttribute(ModAttributes.MAGIC_DAMAGE, event::add);
        ModAttributes.registerAttribute(ModAttributes.ARMOR_PASS, event::add);
        ModAttributes.registerAttribute(ModAttributes.PICKUP_RANGE, event::add);
    }

}
