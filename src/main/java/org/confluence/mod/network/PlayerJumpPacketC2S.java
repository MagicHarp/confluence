package org.confluence.mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record PlayerJumpPacketC2S(float fallDistance) {
    public static void encode(PlayerJumpPacketC2S packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeFloat(packet.fallDistance);
    }

    public static PlayerJumpPacketC2S decode(FriendlyByteBuf friendlyByteBuf) {
        return new PlayerJumpPacketC2S(friendlyByteBuf.readFloat());
    }

    public static void handle(PlayerJumpPacketC2S packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer == null) return;
            //serverPlayer.jumpFromGround();
            serverPlayer.fallDistance = packet.fallDistance;
        });
        context.setPacketHandled(true);
    }
}