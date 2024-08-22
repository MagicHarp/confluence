package org.confluence.mod.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.entity.projectile.SwordProjectile;

import java.util.function.Supplier;

public class SwordShootingPacketC2S {
    private static SwordProjectile projectile;

    public SwordShootingPacketC2S(SwordProjectile projectile) {
        SwordShootingPacketC2S.projectile = projectile;
    }

    public SwordShootingPacketC2S() {
    }

    public static void encode(SwordShootingPacketC2S packet, FriendlyByteBuf friendlyByteBuf) {
    }

    public static SwordShootingPacketC2S decode(FriendlyByteBuf friendlyByteBuf) {
        return new SwordShootingPacketC2S();
    }

    public static void handle(SwordShootingPacketC2S packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            ServerLevel level = player.serverLevel();

            Vec3 lookDirection = player.getViewVector(1.5F);
            Vec3 spawnPos = player.position().add(lookDirection.scale(0.2F));

            projectile.setPos(spawnPos.x, spawnPos.y + 1.5F, spawnPos.z);
            projectile.setDeltaMovement(lookDirection.scale(2.5F));
            projectile.setOwner(player);

            level.addFreshEntity(projectile);
        });
        context.setPacketHandled(true);
    }
}
