package org.confluence.mod.common.event.game;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.mod.Confluence;
import org.confluence.mod.util.PlayerUtils;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = Confluence.MODID)
public final class TickEvents {
    @SubscribeEvent
    public static void levelTick$Post(LevelTickEvent.Post event) {

    }

    @SubscribeEvent
    public static void entityTick$Post(EntityTickEvent.Post event) {

    }

    @SubscribeEvent
    public static void playerTick$Pre(PlayerTickEvent.Pre event) {

    }

    @SubscribeEvent
    public static void playerTick$Post(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (PlayerUtils.isServerNotFake(player)) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            PlayerUtils.regenerateMana(serverPlayer);
        }
    }
}
