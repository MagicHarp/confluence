package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.TabiPacketS2C;
import org.confluence.mod.util.CuriosUtils;

public interface ITabi {
    static void sendMsg(ServerPlayer serverPlayer) {
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new TabiPacketS2C(CuriosUtils.hasCurio(serverPlayer, ITabi.class))
        );
    }

    static void apply(LivingEntity living) {
        float f = living.getYRot() * Mth.DEG_TO_RAD;
        float factor = living.onGround() ? 1.5F : 1.1F;
        living.setDeltaMovement(living.getDeltaMovement().add(-Mth.sin(f) * factor, 0.0D, Mth.cos(f) * factor));
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.tabi");
}
