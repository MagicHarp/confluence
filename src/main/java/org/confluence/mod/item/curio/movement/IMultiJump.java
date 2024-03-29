package org.confluence.mod.item.curio.movement;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.PlayerJumpPacketS2C;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public interface IMultiJump {
    int getJumpCount();

    double getMultiY();

    static void sendMaxJump(ServerPlayer serverPlayer) {
        AtomicInteger maxJumpCount = new AtomicInteger();
        AtomicDouble multiY = new AtomicDouble();
        AtomicBoolean fart = new AtomicBoolean();
        CuriosApi.getCuriosInventory(serverPlayer).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                Item curio = itemHandlerModifiable.getStackInSlot(i).getItem();
                if (curio instanceof IMultiJump iMultiJump) {
                    maxJumpCount.set(Math.max(iMultiJump.getJumpCount(), maxJumpCount.get()));
                    multiY.set(Math.max(iMultiJump.getMultiY(), multiY.get()));
                }
                if(curio instanceof FartInABottle){
                    fart.set(true);
                }
            }
        });
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new PlayerJumpPacketS2C(maxJumpCount.get(), multiY.get(), fart.get())
        );
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.multi_jump");
}
