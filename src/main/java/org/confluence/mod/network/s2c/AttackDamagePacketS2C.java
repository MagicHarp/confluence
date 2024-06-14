package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.InformationHandler;

import java.util.function.Supplier;

public record AttackDamagePacketS2C(float amount, long gameTime) {
    public static void encode(AttackDamagePacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeFloat(packet.amount);
        friendlyByteBuf.writeLong(packet.gameTime);
    }

    public static AttackDamagePacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new AttackDamagePacketS2C(friendlyByteBuf.readFloat(), friendlyByteBuf.readLong());
    }

    public static void handle(AttackDamagePacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> InformationHandler.handleAttackDamage(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
