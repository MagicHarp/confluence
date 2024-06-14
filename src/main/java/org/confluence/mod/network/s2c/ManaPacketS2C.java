package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.ClientPacketHandler;

import java.util.function.Supplier;

public record ManaPacketS2C(int maxMana, int currentMana) {
    public static void encode(ManaPacketS2C manaPacketS2C, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(manaPacketS2C.maxMana());
        friendlyByteBuf.writeInt(manaPacketS2C.currentMana());
    }

    public static ManaPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new ManaPacketS2C(friendlyByteBuf.readInt(), friendlyByteBuf.readInt());
    }

    public static void handle(ManaPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleMana(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
