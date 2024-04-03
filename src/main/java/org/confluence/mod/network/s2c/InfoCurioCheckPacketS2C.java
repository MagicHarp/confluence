package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.item.curio.informational.*;
import org.confluence.mod.network.NetworkHandler;

import java.util.List;

/**
 * @param enabled length == 13
 */
public record InfoCurioCheckPacketS2C(byte[] enabled) {
    public static void encode(InfoCurioCheckPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeByteArray(packet.enabled);
    }

    public static InfoCurioCheckPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new InfoCurioCheckPacketS2C(friendlyByteBuf.readByteArray());
    }

    public static void send(ServerPlayer serverPlayer, List<ItemStack> items) {
        byte watch = 0;
        byte radar = 0;
        byte tallyCounter = 0;
        byte compass = 0;
        byte depthMeter = 0;

        byte mechanicalLens = 0;
        for (ItemStack stack : items) {
            Item item = stack.getItem();
            if (item instanceof MinuteWatch) watch = 1;
            else if (item instanceof HalfHourWatch) watch = 2;
            else if (item instanceof HourWatch) watch = 3;

            if (item instanceof IRadar) radar = 1;
            if (item instanceof ITallyCounter) tallyCounter = 1;
            if (item instanceof ICompass) compass = 1;
            if (item instanceof IDepthMeter) depthMeter = 1;
            if (item instanceof MechanicalLens) mechanicalLens = 1;
        }
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new InfoCurioCheckPacketS2C(new byte[]{
                watch, 0, 0, 0, 0, 0, radar, tallyCounter, 0, 0, compass, depthMeter, mechanicalLens
            })
        );
    }
}
