package org.confluence.mod.common.event.game;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import org.confluence.mod.Confluence;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = Confluence.MODID)
public final class ServerEvents {
    @SubscribeEvent
    public static void serverAboutToStart(ServerAboutToStartEvent event) {

    }

    @SubscribeEvent
    public static void serverStop(ServerStoppedEvent event) {

    }
}
