package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.ClientPacketHandler;

import java.util.function.Supplier;

public record SpecificMoonPacketS2C(int id) {
    public static void encode(SpecificMoonPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(packet.id);
    }

    public static SpecificMoonPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new SpecificMoonPacketS2C(friendlyByteBuf.readInt());
    }

    public static void handle(SpecificMoonPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleSpecificMoon(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
