package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.AttackDamagePacketS2C;

public interface IDPSMeter {
    static Component getInfo(float amount) {
        return Component.translatable(
            "info.confluence.dps_meter",
            "%.2f".formatted(amount)
        );
    }

    static void sendMsg(float amount, Entity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new AttackDamagePacketS2C(amount, serverPlayer.level().getGameTime())
            );
        }
    }
}
