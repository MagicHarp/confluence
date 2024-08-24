package org.confluence.mod.item.curio.construction;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.RightClickSubtractorPacketS2C;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicInteger;

public interface IRightClickSubtractor {
    static void sendMsg(ServerPlayer serverPlayer) {
        AtomicInteger atomic = new AtomicInteger();
        CuriosApi.getCuriosInventory(serverPlayer).ifPresent(handler -> {
            IItemHandlerModifiable itemHandlerModifiable = handler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                Item item = itemHandlerModifiable.getStackInSlot(i).getItem();
                if (item == CurioItems.HAND_OF_CREATION.get()) {
                    atomic.set(3);
                    break;
                }
                if (item == CurioItems.ARCHITECT_GIZMO_PACK.get()) {
                    atomic.set(2);
                }
                if (atomic.get() < 2 && item instanceof IRightClickSubtractor) atomic.addAndGet(1);
            }
        });
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new RightClickSubtractorPacketS2C(atomic.get())
        );
    }
}
