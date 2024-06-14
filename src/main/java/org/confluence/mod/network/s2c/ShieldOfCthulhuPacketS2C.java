package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.ClientPacketHandler;

import java.util.function.Supplier;

public record ShieldOfCthulhuPacketS2C(boolean has) {
    public static void encode(ShieldOfCthulhuPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.has);
    }

    public static ShieldOfCthulhuPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new ShieldOfCthulhuPacketS2C(friendlyByteBuf.readBoolean());
    }

    public static void handle(ShieldOfCthulhuPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleCthulhu(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
