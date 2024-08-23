package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.PlayerJumpHandler;

import java.util.function.Supplier;

public record PlayerFlyPacketS2C(int maxFlyTicks, float flySpeed, boolean glide) {
    public static void encode(PlayerFlyPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(packet.maxFlyTicks);
        friendlyByteBuf.writeFloat(packet.flySpeed);
        friendlyByteBuf.writeBoolean(packet.glide);
    }

    public static PlayerFlyPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new PlayerFlyPacketS2C(friendlyByteBuf.readInt(), friendlyByteBuf.readFloat(), friendlyByteBuf.readBoolean());
    }

    public static void handle(PlayerFlyPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> PlayerJumpHandler.handleFlyPacket(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
