package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.handler.ClientBossBarTracker;
import org.mesdag.portlib.network.IPortPacket;
import org.mesdag.portlib.network.codec.PortStreamCodec;

import java.util.UUID;

public record BossBarSyncPacketS2C(UUID eventId, ResourceLocation entityType, float health,
                                   float maximumHealth,
                                   boolean visible) implements IPortPacket.S2C {
    public static final ResourceLocation ID = Confluence.asResource("boss_bar_sync");
    public static final PortStreamCodec<FriendlyByteBuf, BossBarSyncPacketS2C> STREAM_CODEC = new PortStreamCodec<>() {
        @Override
        public BossBarSyncPacketS2C decode(FriendlyByteBuf buffer) {
            return new BossBarSyncPacketS2C(buffer.readUUID(), buffer.readResourceLocation(),
                    buffer.readFloat(), buffer.readFloat(), buffer.readBoolean());
        }

        @Override
        public void encode(FriendlyByteBuf buffer, BossBarSyncPacketS2C value) {
            buffer.writeUUID(value.eventId);
            buffer.writeResourceLocation(value.entityType);
            buffer.writeFloat(value.health);
            buffer.writeFloat(value.maximumHealth);
            buffer.writeBoolean(value.visible);
        }
    };

    @Override
    public ResourceLocation identifier() {
        return ID;
    }

    @Override
    public void work(Player player) {
        if (visible) {
            ClientBossBarTracker.synchronize(eventId, entityType, health, maximumHealth);
        } else {
            ClientBossBarTracker.remove(eventId);
        }
    }
}
