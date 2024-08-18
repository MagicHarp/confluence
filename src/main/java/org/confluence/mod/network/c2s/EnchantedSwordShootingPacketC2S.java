package org.confluence.mod.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.entity.projectile.EnchantedSwordProjectile;

import java.util.function.Supplier;

public record EnchantedSwordShootingPacketC2S() { // todo 兼容其他剑气
    public static void encode(EnchantedSwordShootingPacketC2S packet, FriendlyByteBuf friendlyByteBuf) {}

    public static EnchantedSwordShootingPacketC2S decode(FriendlyByteBuf friendlyByteBuf) {
        return new EnchantedSwordShootingPacketC2S();
    }

    public static void handle(EnchantedSwordShootingPacketC2S packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            ServerLevel level = player.serverLevel();

            Vec3 lookDirection = player.getViewVector(1.5F);
            Vec3 spawnPos = player.position().add(lookDirection.scale(0.2F));

            EnchantedSwordProjectile projectile = new EnchantedSwordProjectile(ModEntities.ENCHANTED_SWORD_PROJECTILE.get(), level);
            projectile.setPos(spawnPos.x, spawnPos.y + 1.5F, spawnPos.z);
            projectile.setDeltaMovement(lookDirection.scale(2.5F));
            projectile.setOwner(player);

            level.addFreshEntity(projectile);
        });
        context.setPacketHandled(true);
    }
}
