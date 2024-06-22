package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.ClientPacketHandler;

import java.util.function.Supplier;

public record RightClickSubtractorPacketS2C(int amount) {
    public static void encode(RightClickSubtractorPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(packet.amount);
    }

    public static RightClickSubtractorPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new RightClickSubtractorPacketS2C(friendlyByteBuf.readInt());
    }

    public static void handle(RightClickSubtractorPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleDivisor(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
