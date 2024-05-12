package org.confluence.mod.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.effect.beneficial.GravitationEffect;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.BroadcastGravitationRotPacketS2C;
import org.confluence.mod.util.IEntity;

import java.util.function.Supplier;

public record GravitationPacketC2S(boolean enable) {
    public static void encode(GravitationPacketC2S packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.enable);
    }

    public static GravitationPacketC2S decode(FriendlyByteBuf friendlyByteBuf) {
        return new GravitationPacketC2S(friendlyByteBuf.readBoolean());
    }

    public static void handle(GravitationPacketC2S packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer == null) return;
            serverPlayer.resetFallDistance();
            AttributeMap attributeMap = serverPlayer.getAttributes();
            if (packet.enable) {
                attributeMap.addTransientAttributeModifiers(GravitationEffect.GRAVITY);
            } else {
                AttributeInstance attributeInstance = attributeMap.getInstance(ForgeMod.ENTITY_GRAVITY.get());
                if (attributeInstance != null) {
                    attributeInstance.removeModifier(GravitationEffect.GRAVITY_UUID);
                }
            }
            ((IEntity) serverPlayer).c$setShouldRot(packet.enable);
            NetworkHandler.CHANNEL.send(
                PacketDistributor.ALL.noArg(),
                new BroadcastGravitationRotPacketS2C(serverPlayer.getId(), packet.enable)
            );
        });
        context.setPacketHandled(true);
    }
}
