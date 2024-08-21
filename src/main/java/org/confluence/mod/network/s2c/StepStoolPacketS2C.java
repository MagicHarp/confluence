package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.StepStoolHandler;

import java.util.function.Supplier;

public record StepStoolPacketS2C(int maxStep) {
    public static void encode(StepStoolPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(packet.maxStep);
    }

    public static StepStoolPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new StepStoolPacketS2C(friendlyByteBuf.readInt());
    }

    public static void handle(StepStoolPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> StepStoolHandler.handlePacket(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
