package org.confluence.mod.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record FallDistancePacketC2S(float distance) {
    public static void encode(FallDistancePacketC2S packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeFloat(packet.distance);
    }

    public static FallDistancePacketC2S decode(FriendlyByteBuf friendlyByteBuf) {
        return new FallDistancePacketC2S(friendlyByteBuf.readFloat());
    }

    public static void handle(FallDistancePacketC2S packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer != null) {
                serverPlayer.fallDistance = packet.distance();
            }
        });
        context.setPacketHandled(true);
    }
}
