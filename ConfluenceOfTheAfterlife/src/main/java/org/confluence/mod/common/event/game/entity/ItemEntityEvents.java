package org.confluence.mod.common.event.game.entity;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.item.ItemExpireEvent;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import org.confluence.mod.Confluence;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = Confluence.MODID)
public final class ItemEntityEvents {
    @SubscribeEvent
    public static void itemExpire(ItemExpireEvent event) {

    }

    @SubscribeEvent
    public static void itemToss(ItemTossEvent event) {

    }
}
