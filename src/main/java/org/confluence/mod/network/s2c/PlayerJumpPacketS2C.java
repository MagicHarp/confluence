package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.PlayerJumpHandler;

import java.util.function.Supplier;

public record PlayerJumpPacketS2C(float fartSpeed, float sandstormSpeed, int sandstormTicks, float blizzardSpeed, int blizzardTicks, float tsunamiSpeed, float cloudSpeed) {
    public static void encode(PlayerJumpPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeFloat(packet.fartSpeed);
        friendlyByteBuf.writeFloat(packet.sandstormSpeed);
        friendlyByteBuf.writeInt(packet.sandstormTicks);
        friendlyByteBuf.writeFloat(packet.blizzardSpeed);
        friendlyByteBuf.writeInt(packet.blizzardTicks);
        friendlyByteBuf.writeFloat(packet.tsunamiSpeed);
        friendlyByteBuf.writeFloat(packet.cloudSpeed);
    }

    public static PlayerJumpPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new PlayerJumpPacketS2C(
            friendlyByteBuf.readFloat(),
            friendlyByteBuf.readFloat(),
            friendlyByteBuf.readInt(),
            friendlyByteBuf.readFloat(),
            friendlyByteBuf.readInt(),
            friendlyByteBuf.readFloat(),
            friendlyByteBuf.readFloat()
        );
    }

    public static void handle(PlayerJumpPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> PlayerJumpHandler.handleJumpPacket(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
