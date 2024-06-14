package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.ClientPacketHandler;

import java.util.function.Supplier;

public record HolyWaterColorUpdatePacketS2C(boolean show) {
    public static void encode(HolyWaterColorUpdatePacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.show);
    }

    public static HolyWaterColorUpdatePacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new HolyWaterColorUpdatePacketS2C(friendlyByteBuf.readBoolean());
    }

    public static void handle(HolyWaterColorUpdatePacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleHolyWater(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
