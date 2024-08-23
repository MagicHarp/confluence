package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.PacketDistributor;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.PlayerFlyPacketS2C;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public interface IMayFly {
    default int getFlyTicks() {
        return 32;
    }

    default float getFlySpeed() {
        return 0.3F;
    }

    default boolean couldGlide() {
        return false;
    }

    static void sendMsg(ServerPlayer serverPlayer) {
        AtomicInteger maxFlyTicks = new AtomicInteger();
        MutableFloat flySpeed = new MutableFloat();
        AtomicBoolean glide = new AtomicBoolean();
        CuriosApi.getCuriosInventory(serverPlayer).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                Item curio = itemHandlerModifiable.getStackInSlot(i).getItem();
                if (curio instanceof IMayFly iMayFly) {
                    maxFlyTicks.set(Math.max(iMayFly.getFlyTicks(), maxFlyTicks.get()));
                    flySpeed.setValue(Math.max(iMayFly.getFlySpeed(), flySpeed.getValue()));
                    if (iMayFly.couldGlide()) glide.set(true);
                }
            }
        });
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new PlayerFlyPacketS2C(maxFlyTicks.get(), flySpeed.getValue(), glide.get())
        );
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.may_fly");
}
