package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.InformationHandler;

import java.util.function.Supplier;

public record EntityKilledPacketS2C(int amount, ResourceLocation type) {
    public static void encode(EntityKilledPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(packet.amount);
        friendlyByteBuf.writeResourceLocation(packet.type);
    }

    public static EntityKilledPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new EntityKilledPacketS2C(friendlyByteBuf.readInt(), friendlyByteBuf.readResourceLocation());
    }

    public static void handle(EntityKilledPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> InformationHandler.handleEntityKilled(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
