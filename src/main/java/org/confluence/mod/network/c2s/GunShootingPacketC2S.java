package org.confluence.mod.network.c2s;

import net.minecraft.client.Minecraft;
import net.minecraft.client.telemetry.TelemetryProperty;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.entity.projectile.BaseAmmoEntity;
import org.confluence.mod.item.gun.AbstractGunItem;
import org.confluence.mod.item.gun.AmmoItems;
import org.confluence.mod.item.gun.BaseAmmoItem;
import org.confluence.mod.misc.ModAttributes;

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
                if(player.getCooldowns().isOnCooldown(abstractGunItem)) return;
                Tuple<ItemStack, BaseAmmoItem> ammoTuple = abstractGunItem.getAmmoTuple(player);
                if (ammoTuple == null) return;
                ItemStack ammoStack = ammoTuple.getA();
                Level level = player.level();
                BaseAmmoItem ammoItem = ammoTuple.getB();
                BaseAmmoEntity ammoEntity = ammoItem.getAmmoEntity(player, level);
                ammoEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, getVelocity(player, abstractGunItem, ammoEntity), 0.0F);
                level.addFreshEntity(ammoEntity);



                if (ammoItem != AmmoItems.ENDLESS_MUSKET_POUCH.get()
                 && Minecraft.getInstance().gameMode.canHurtPlayer()) //创造模式不消耗弹药
                {
                    ammoStack.shrink(1);
                }
                player.getCooldowns().addCooldown(abstractGunItem, abstractGunItem.getCoolDown());

            }
        });
        context.setPacketHandled(true);
    }

    public static float getVelocity(Player player, AbstractGunItem gunItem, BaseAmmoEntity ammoEntity) {
        BaseAmmoEntity.Variant variant = ammoEntity.getVariant();
        float velocity = (gunItem.getShootingSpeed() + variant.velocity) * variant.multiplier;
        AttributeInstance attributeInstance = player.getAttribute(ModAttributes.getRangedVelocity());
        if (attributeInstance != null) velocity *= (float) attributeInstance.getValue();
        return velocity;
    }
}
