package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import org.confluence.mod.command.GamePhase;

public record GamePhasePacketS2C(GamePhase gamePhase) {
    public static void encode(GamePhasePacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeVarInt(packet.gamePhase.ordinal());
    }

    public static GamePhasePacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new GamePhasePacketS2C(GamePhase.values()[friendlyByteBuf.readVarInt()]);
    }
}
