package org.confluence.mod.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.item.curio.movement.BaseSpeedBoots;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Supplier;

public record SpeedBootsNBTPacketC2S(int slot, int value) {
    public static void encode(SpeedBootsNBTPacketC2S packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(packet.slot);
        friendlyByteBuf.writeInt(packet.value);
    }

    public static SpeedBootsNBTPacketC2S decode(FriendlyByteBuf friendlyByteBuf) {
        return new SpeedBootsNBTPacketC2S(friendlyByteBuf.readInt(), friendlyByteBuf.readInt());
    }

    public static void handle(SpeedBootsNBTPacketC2S packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer == null) return;
            CuriosApi.getCuriosInventory(serverPlayer).ifPresent(curiosItemHandler -> {
                ItemStack itemStack = curiosItemHandler.getEquippedCurios().getStackInSlot(packet.slot);
                if (itemStack.getItem() instanceof BaseSpeedBoots) {
                    itemStack.getOrCreateTag().putInt("speed", packet.value);
                }
            });
        });
        context.setPacketHandled(true);
    }
}
