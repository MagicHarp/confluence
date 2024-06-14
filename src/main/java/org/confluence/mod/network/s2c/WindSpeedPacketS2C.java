package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.InformationHandler;

import java.util.function.Supplier;

public record WindSpeedPacketS2C(float speed) {
    public static void encode(WindSpeedPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeFloat(packet.speed);
    }

    public static WindSpeedPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new WindSpeedPacketS2C(friendlyByteBuf.readFloat());
    }

    public static void handle(WindSpeedPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> InformationHandler.handleWindSpeed(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
