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

import java.util.concurrent.atomic.AtomicInteger;

public interface IMultiJump {
    double getJumpSpeed();

    static void sendMsg(ServerPlayer serverPlayer) {
        AtomicDouble fartSpeed = new AtomicDouble();
        AtomicDouble sandstormSpeed = new AtomicDouble();
        AtomicInteger sandstormTicks = new AtomicInteger();
        AtomicDouble blizzardSpeed = new AtomicDouble();
        AtomicInteger blizzardTicks = new AtomicInteger();
        AtomicDouble tsunamiSpeed = new AtomicDouble();
        AtomicDouble cloudSpeed = new AtomicDouble();
        CuriosApi.getCuriosInventory(serverPlayer).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                Item curio = itemHandlerModifiable.getStackInSlot(i).getItem();
                if (curio instanceof FartInABottle fart) {
                    fartSpeed.set(fart.getJumpSpeed());
                } else if (curio instanceof SandstormInABottle sandstorm) {
                    sandstormSpeed.set(sandstorm.getJumpSpeed());
                    sandstormTicks.set(sandstorm.getJumpTicks());
                } else if (curio instanceof BlizzardInABottle blizzard) {
                    blizzardSpeed.set(blizzard.getJumpSpeed());
                    blizzardTicks.set(blizzard.getJumpTicks());
                } else if (curio instanceof TsunamiInABottle tsunami) {
                    tsunamiSpeed.set(tsunami.getJumpSpeed());
                } else if (curio instanceof CloudInABottle cloud) {
                    cloudSpeed.set(cloud.getJumpSpeed());
                }
            }
        });
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new PlayerJumpPacketS2C(
                fartSpeed.get(),
                sandstormSpeed.get(),
                sandstormTicks.get(),
                blizzardSpeed.get(),
                blizzardTicks.get(),
                tsunamiSpeed.get(),
                cloudSpeed.get()
            )
        );
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.multi_jump");
}
