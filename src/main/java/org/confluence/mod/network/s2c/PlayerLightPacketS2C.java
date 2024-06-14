package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.ClientPacketHandler;

import java.util.UUID;
import java.util.function.Supplier;

public record PlayerLightPacketS2C(UUID playerUUID, boolean enable) {
    public static void encode(PlayerLightPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeUUID(packet.playerUUID);
        friendlyByteBuf.writeBoolean(packet.enable);
    }

    public static PlayerLightPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new PlayerLightPacketS2C(friendlyByteBuf.readUUID(), friendlyByteBuf.readBoolean());
    }

    public static void handle(PlayerLightPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleLight(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
