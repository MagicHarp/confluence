package org.confluence.mod.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.curio.IMultiJump;
import org.confluence.mod.network.EchoBlockVisibilityPacketS2C;
import org.confluence.mod.network.MechanicalBlockVisibilityPacketS2C;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.PlayerJumpPacketS2C;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.event.CurioChangeEvent;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.concurrent.atomic.AtomicInteger;

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

            sendMaxJump(serverPlayer);
        }
    }

    private static void echo(ItemStack itemStack, ServerPlayer serverPlayer, boolean value) {
        if (itemStack.is(CurioItems.SPECTRE_GOGGLES.get())) {
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new EchoBlockVisibilityPacketS2C(value)
            );
        }
    }

    private static void mechanical(ItemStack itemStack, ServerPlayer serverPlayer, boolean value) {
        if (itemStack.is(CurioItems.MECHANICAL_LENS.get())) {
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new MechanicalBlockVisibilityPacketS2C(value)
            );
        }
    }

    private static void sendMaxJump(ServerPlayer serverPlayer) {
        AtomicInteger count = new AtomicInteger(1);
        CuriosApi.getCuriosInventory(serverPlayer).ifPresent(curiosItemHandler -> curiosItemHandler
            .getCurios().values().forEach(curioStacksHandler -> {
                IDynamicStackHandler dynamicStackHandler = curioStacksHandler.getStacks();
                for (int i = 0; i < dynamicStackHandler.getSlots(); i++) {
                    ItemStack curio = dynamicStackHandler.getStackInSlot(i);
                    if (curio.getItem() instanceof IMultiJump iMultiJump) {
                        count.addAndGet(iMultiJump.getJumpTimes());
                    }
                }
            }));
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new PlayerJumpPacketS2C(count.get())
        );
    }
}
