package org.confluence.mod.item.curio.movement;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.PlayerOneTimeJumpPacketS2C;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicInteger;

public interface IOneTimeJump {
    int getJumpTicks();

    double getJumpSpeed();

    static void sendMaxJump(ServerPlayer serverPlayer) {
        AtomicInteger maxJumpTicks = new AtomicInteger();
        AtomicDouble jumpSpeed = new AtomicDouble();
        CuriosApi.getCuriosInventory(serverPlayer).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                ItemStack curio = itemHandlerModifiable.getStackInSlot(i);
                if (curio.getItem() instanceof IOneTimeJump iOneTimeJump) {
                    maxJumpTicks.set(Math.max(iOneTimeJump.getJumpTicks(), maxJumpTicks.get()));
                    jumpSpeed.set(Math.max(iOneTimeJump.getJumpSpeed(), jumpSpeed.get()));
                }
            }
        });
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new PlayerOneTimeJumpPacketS2C(maxJumpTicks.get(), jumpSpeed.get())
        );
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.multi_jump");
}
