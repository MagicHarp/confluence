package org.confluence.mod.item.curio.movement;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.PlayerFlyPacketS2C;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicInteger;

public interface IMayFly {
    int getFlyTicks();

    double getFlySpeed();

    static void sendMaxFly(ServerPlayer serverPlayer) {
        AtomicInteger maxFlyTicks = new AtomicInteger();
        AtomicDouble maxFlySpeed = new AtomicDouble();
        CuriosApi.getCuriosInventory(serverPlayer).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                ItemStack curio = itemHandlerModifiable.getStackInSlot(i);
                if (curio.getItem() instanceof IMayFly iMayFly) {
                    int ticks = iMayFly.getFlyTicks();
                    if (ticks > maxFlyTicks.get()) {
                        maxFlyTicks.set(ticks);
                    }
                    double speed = iMayFly.getFlySpeed();
                    if (speed > maxFlySpeed.get()) {
                        maxFlySpeed.set(speed);
                    }
                }
            }
        });
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new PlayerFlyPacketS2C(maxFlyTicks.get(), maxFlySpeed.get())
        );
    }
}
