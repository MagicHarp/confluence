package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.PlayerJumpHandler;

import java.util.function.Supplier;

public record PlayerJumpPacketS2C(double fartSpeed, double sandstormSpeed, int sandstormTicks, double blizzardSpeed, int blizzardTicks, double tsunamiSpeed, double cloudSpeed) {
    public static void encode(PlayerJumpPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeDouble(packet.fartSpeed);
        friendlyByteBuf.writeDouble(packet.sandstormSpeed);
        friendlyByteBuf.writeInt(packet.sandstormTicks);
        friendlyByteBuf.writeDouble(packet.blizzardSpeed);
        friendlyByteBuf.writeInt(packet.blizzardTicks);
        friendlyByteBuf.writeDouble(packet.tsunamiSpeed);
        friendlyByteBuf.writeDouble(packet.cloudSpeed);
    }

    public static PlayerJumpPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new PlayerJumpPacketS2C(
            friendlyByteBuf.readDouble(),
            friendlyByteBuf.readDouble(),
            friendlyByteBuf.readInt(),
            friendlyByteBuf.readDouble(),
            friendlyByteBuf.readInt(),
            friendlyByteBuf.readDouble(),
            friendlyByteBuf.readDouble()
        );
    }

    public static void handle(PlayerJumpPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> PlayerJumpHandler.handleJumpPacket(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
