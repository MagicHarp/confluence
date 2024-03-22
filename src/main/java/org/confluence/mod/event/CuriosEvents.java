package org.confluence.mod.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ConfluenceItems;
import org.confluence.mod.network.EchoBlockVisibilityPacket;
import org.confluence.mod.network.MechanicalBlockVisibilityPacket;
import org.confluence.mod.network.NetworkHandler;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CuriosEvents {
    @SubscribeEvent
    public static void curioChange(CurioChangeEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            ItemStack from = event.getFrom();
            ItemStack to = event.getTo();
            echo(from, serverPlayer, false);
            echo(to, serverPlayer, true);
            mechanical(from, serverPlayer, false);
            mechanical(to, serverPlayer, true);
        }
    }

    private static void echo(ItemStack itemStack, ServerPlayer serverPlayer, boolean value) {
        if (itemStack.is(ConfluenceItems.SPECTRE_GOGGLES.get())) {
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new EchoBlockVisibilityPacket(value)
            );
        }
    }

    private static void mechanical(ItemStack itemStack, ServerPlayer serverPlayer, boolean value) {
        if (itemStack.is(ConfluenceItems.MECHANICAL_LENS.get())) {
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new MechanicalBlockVisibilityPacket(value)
            );
        }
    }
}
