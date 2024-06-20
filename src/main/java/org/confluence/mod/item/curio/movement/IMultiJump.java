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
        AtomicDouble fartSpeed = new AtomicDouble(-1.0);
        AtomicDouble sandstormSpeed = new AtomicDouble(-1.0);
        AtomicInteger sandstormTicks = new AtomicInteger();
        AtomicDouble blizzardSpeed = new AtomicDouble(-1.0);
        AtomicInteger blizzardTicks = new AtomicInteger();
        AtomicDouble tsunamiSpeed = new AtomicDouble(-1.0);
        AtomicDouble cloudSpeed = new AtomicDouble(-1.0);
        CuriosApi.getCuriosInventory(serverPlayer).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                Item curio = itemHandlerModifiable.getStackInSlot(i).getItem();
                if (curio instanceof FartInAJar fart) {
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
                } else if (curio instanceof BundleOfBalloons) {
                    sandstormSpeed.set(SandstormInABottle.SPEED);
                    sandstormTicks.set(SandstormInABottle.TICKS);
                    blizzardSpeed.set(BlizzardInABottle.SPEED);
                    blizzardTicks.set(BlizzardInABottle.TICKS);
                    cloudSpeed.set(CloudInABottle.SPEED);
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
