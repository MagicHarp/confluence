package org.confluence.mod.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record PlayerJumpPacketC2S(boolean jumpBySelf, boolean fart) {
    public static void encode(PlayerJumpPacketC2S packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.jumpBySelf);
        friendlyByteBuf.writeBoolean(packet.fart);
    }

    public static PlayerJumpPacketC2S decode(FriendlyByteBuf friendlyByteBuf) {
        return new PlayerJumpPacketC2S(friendlyByteBuf.readBoolean(), friendlyByteBuf.readBoolean());
    }

    public static void handle(PlayerJumpPacketC2S packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer == null) return;
            serverPlayer.resetFallDistance();
            serverPlayer.hasImpulse = true;
            if (packet.jumpBySelf) {
                serverPlayer.awardStat(Stats.JUMP);
                serverPlayer.causeFoodExhaustion(serverPlayer.isSprinting() ? 0.2F : 0.05F);
            }
            if (packet.fart) {
                serverPlayer.level().playSound(serverPlayer, serverPlayer.getOnPos(), SoundEvents.BELL_BLOCK, SoundSource.PLAYERS);
            }
        });
        context.setPacketHandled(true);
    }
}
