package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.GravitationHandler;

import java.util.function.Supplier;

public record GravityGlobePacketS2C(boolean has) {
    public static void encode(GravityGlobePacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.has);
    }

    public static GravityGlobePacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new GravityGlobePacketS2C(friendlyByteBuf.readBoolean());
    }

    public static void handle(GravityGlobePacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> GravitationHandler.handleGlobe(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
