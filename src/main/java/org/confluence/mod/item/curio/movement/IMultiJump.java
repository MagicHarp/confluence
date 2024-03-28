package org.confluence.mod.item.curio.movement;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.PlayerJumpPacketS2C;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicInteger;

public interface IMultiJump {
    int getJumpTimes();

    static void sendMaxJump(ServerPlayer serverPlayer) {
        AtomicInteger maxJump = new AtomicInteger();
        AtomicInteger height = new AtomicInteger();
        CuriosApi.getCuriosInventory(serverPlayer).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                ItemStack curio = itemHandlerModifiable.getStackInSlot(i);
                if (curio.getItem() instanceof IMultiJump iMultiJump) {
                    int times = iMultiJump.getJumpTimes();
                    if (times > maxJump.get()) {
                        maxJump.set(times);
                    }
                    height.addAndGet(times);
                }
            }
        });
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new PlayerJumpPacketS2C(maxJump.get(), height.get() > 1 ? height.get() * 0.7 : 1)
        );
    }
}
