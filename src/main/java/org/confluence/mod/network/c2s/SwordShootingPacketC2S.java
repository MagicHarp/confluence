package org.confluence.mod.network.c2s;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.item.sword.ISwordProjectile;
import org.confluence.mod.item.sword.StarFurySword;

import java.util.function.Supplier;

public record SwordShootingPacketC2S(Item item) {
    public static void encode(SwordShootingPacketC2S packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeId(BuiltInRegistries.ITEM, packet.item);
    }

    public static SwordShootingPacketC2S decode(FriendlyByteBuf friendlyByteBuf) {
        return new SwordShootingPacketC2S(friendlyByteBuf.readById(BuiltInRegistries.ITEM));
    }

    public static void handle(SwordShootingPacketC2S packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            ItemStack mainHandItem = player.getMainHandItem();
            if (mainHandItem.is(packet.item) && packet.item instanceof ISwordProjectile swordProjectile) {
                swordProjectile.genProjectile(player);
            }
        });
        context.setPacketHandled(true);
    }
}
