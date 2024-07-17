package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.PlayerClimbPacketS2C;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicInteger;

public interface IWallClimb {
    default boolean fullyWallClimb() {
        return false;
    }

    static void sendMsg(ServerPlayer serverPlayer) {
        AtomicInteger atomic = new AtomicInteger();
        CuriosApi.getCuriosInventory(serverPlayer).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                Item curio = itemHandlerModifiable.getStackInSlot(i).getItem();
                if (curio instanceof IWallClimb wallClimb) {
                    if (wallClimb.fullyWallClimb()) {
                        atomic.set(2);
                        return;
                    } else {
                        atomic.addAndGet(1);
                    }
                }
                if (atomic.get() == 2) return;
            }
        });
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new PlayerClimbPacketS2C(atomic.get())
        );
    }

    Component WALL_CLIMB = Component.translatable("curios.tooltip.wall_climb");
    Component WALL_SLIDE = Component.translatable("curios.tooltip.wall_slide");
}
