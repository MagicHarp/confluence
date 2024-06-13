package org.confluence.mod.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.capability.prefix.PrefixProvider;
import org.confluence.mod.entity.projectile.BaseAmmoEntity;
import org.confluence.mod.item.gun.AbstractGunItem;
import org.confluence.mod.item.gun.AmmoItems;
import org.confluence.mod.item.gun.BaseAmmoItem;

import java.util.function.Supplier;

public record GunShootingPacketC2S(boolean fromMainHand) {
    public static void encode(GunShootingPacketC2S packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.fromMainHand);
    }

    public static GunShootingPacketC2S decode(FriendlyByteBuf friendlyByteBuf) {
        return new GunShootingPacketC2S(friendlyByteBuf.readBoolean());
    }

    public static void handle(GunShootingPacketC2S packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            ItemStack itemStack = player.getItemInHand(packet.fromMainHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
            if (itemStack.getItem() instanceof AbstractGunItem abstractGunItem) {
                Tuple<ItemStack, BaseAmmoItem> ammoTuple = abstractGunItem.getAmmoTuple(player);
                if (ammoTuple == null) return;
                ItemStack ammoStack = ammoTuple.getA();
                Level level = player.level();
                BaseAmmoItem ammoItem = ammoTuple.getB();
                BaseAmmoEntity ammoEntity = ammoItem.getAmmoEntity(ammoStack, player, level);
                ammoEntity.shoot(player.getXRot(), player.getYRot(), 0.0F, getVelocity(itemStack, abstractGunItem, ammoEntity), 0.0F);
                level.addFreshEntity(ammoEntity);
                if (ammoItem != AmmoItems.ENDLESS_MUSKET_POUCH.get()) {
                    ammoStack.shrink(1);
                }
                int coolDown = abstractGunItem.getCoolDown();
                if (coolDown > 0) player.getCooldowns().addCooldown(abstractGunItem, coolDown);
            }
        });
        context.setPacketHandled(true);
    }

    public static float getVelocity(ItemStack itemStack, AbstractGunItem gunItem, BaseAmmoEntity ammoEntity) {
        BaseAmmoEntity.Variant variant = ammoEntity.getVariant();
        float velocity = (gunItem.getShootingSpeed() + variant.velocity) * variant.multiplier;
        return PrefixProvider.getVelocity(itemStack, velocity);
    }
}
