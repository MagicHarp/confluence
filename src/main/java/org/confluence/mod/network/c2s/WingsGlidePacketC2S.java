package org.confluence.mod.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.item.curio.movement.BaseWings;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Supplier;

public record WingsGlidePacketC2S(boolean onFly, int slot) {
    public static void encode(WingsGlidePacketC2S packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.onFly);
        friendlyByteBuf.writeInt(packet.slot);
    }

    public static WingsGlidePacketC2S decode(FriendlyByteBuf friendlyByteBuf) {
        return new WingsGlidePacketC2S(friendlyByteBuf.readBoolean(), friendlyByteBuf.readInt());
    }

    public static void handle(WingsGlidePacketC2S packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer == null) return;
            CuriosApi.getCuriosInventory(serverPlayer).ifPresent(curiosItemHandler -> {
                ItemStack itemStack = curiosItemHandler.getEquippedCurios().getStackInSlot(packet.slot);
                if (itemStack.getItem() instanceof BaseWings) {
                    itemStack.getOrCreateTag().putBoolean("onFly", packet.onFly);
                }
            });
        });
        context.setPacketHandled(true);
    }
}
