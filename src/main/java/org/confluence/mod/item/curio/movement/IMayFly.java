package org.confluence.mod.item.curio.movement;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.PlayerFlyPacketS2C;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicInteger;

public interface IMayFly {
    int getFlyTicks();

    double getFlySpeed();

    static void sendMsg(ServerPlayer serverPlayer) {
        AtomicInteger maxFlyTicks = new AtomicInteger();
        AtomicDouble flySpeed = new AtomicDouble();
        CuriosApi.getCuriosInventory(serverPlayer).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                Item curio = itemHandlerModifiable.getStackInSlot(i).getItem();
                if (curio instanceof IMayFly iMayFly) {
                    maxFlyTicks.set(Math.max(iMayFly.getFlyTicks(), maxFlyTicks.get()));
                    flySpeed.set(Math.max(iMayFly.getFlySpeed(), flySpeed.get()));
                }
            }
        });
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new PlayerFlyPacketS2C(maxFlyTicks.get(), flySpeed.get())
        );
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.may_fly");
}
