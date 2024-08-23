package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.PacketDistributor;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.PlayerJumpPacketS2C;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicInteger;

public interface IMultiJump {
    float getJumpSpeed();

    static void sendMsg(ServerPlayer serverPlayer) {
        MutableFloat fartSpeed = new MutableFloat(-1.0F);
        MutableFloat sandstormSpeed = new MutableFloat(-1.0F);
        AtomicInteger sandstormTicks = new AtomicInteger();
        MutableFloat blizzardSpeed = new MutableFloat(-1.0F);
        AtomicInteger blizzardTicks = new AtomicInteger();
        MutableFloat tsunamiSpeed = new MutableFloat(-1.0F);
        MutableFloat cloudSpeed = new MutableFloat(-1.0F);
        CuriosApi.getCuriosInventory(serverPlayer).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                Item curio = itemHandlerModifiable.getStackInSlot(i).getItem();
                if (curio instanceof FartInAJar fart) {
                    fartSpeed.setValue(fart.getJumpSpeed());
                } else if (curio instanceof SandstormInABottle sandstorm) {
                    sandstormSpeed.setValue(sandstorm.getJumpSpeed());
                    sandstormTicks.set(sandstorm.getJumpTicks());
                } else if (curio instanceof BlizzardInABottle blizzard) {
                    blizzardSpeed.setValue(blizzard.getJumpSpeed());
                    blizzardTicks.set(blizzard.getJumpTicks());
                } else if (curio instanceof TsunamiInABottle tsunami) {
                    tsunamiSpeed.setValue(tsunami.getJumpSpeed());
                } else if (curio instanceof CloudInABottle cloud) {
                    cloudSpeed.setValue(cloud.getJumpSpeed());
                } else if (curio instanceof BundleOfBalloons) {
                    sandstormSpeed.setValue(SandstormInABottle.SPEED);
                    sandstormTicks.set(SandstormInABottle.TICKS);
                    blizzardSpeed.setValue(BlizzardInABottle.SPEED);
                    blizzardTicks.set(BlizzardInABottle.TICKS);
                    cloudSpeed.setValue(CloudInABottle.SPEED);
                }
            }
        });
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new PlayerJumpPacketS2C(
                fartSpeed.getValue(),
                sandstormSpeed.getValue(),
                sandstormTicks.get(),
                blizzardSpeed.getValue(),
                blizzardTicks.get(),
                tsunamiSpeed.getValue(),
                cloudSpeed.getValue()
            )
        );
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.multi_jump");
}
