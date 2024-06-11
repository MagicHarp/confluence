package org.confluence.mod.item.curio.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.confluence.mod.integration.bettercombat.BetterCombatHelper;
import org.confluence.mod.mixin.client.MinecraftAccessor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.AutoAttackPacketS2C;
import org.confluence.mod.util.CuriosUtils;

public interface IAutoAttack {
    static void sendMsg(ServerPlayer serverPlayer) {
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new AutoAttackPacketS2C(CuriosUtils.hasCurio(serverPlayer, IAutoAttack.class))
        );
    }

    static void apply(Minecraft minecraft, LocalPlayer localPlayer) {
        if (BetterCombatHelper.isBetterCombatLoaded()) {
            ItemStack itemStack = localPlayer.getItemInHand(InteractionHand.MAIN_HAND);
            if (BetterCombatHelper.hasWeaponAttributes(itemStack)) return;
        }
        if (ClientPacketHandler.couldAutoAttack() && minecraft.options.keyAttack.isDown()) {
            if (localPlayer.getAttackStrengthScale(0.5F) < 1.0F) return;
            MinecraftAccessor accessor = (MinecraftAccessor) minecraft;
            if (accessor.getMissTime() > 0) accessor.setMissTime(0);
            double reach = localPlayer.getEntityReach();
            Vec3 from = localPlayer.getEyePosition(1.0F);
            Vec3 viewVector = localPlayer.getViewVector(1.0F);
            Vec3 to = from.add(viewVector.x * reach, viewVector.y * reach, viewVector.z * reach);
            EntityHitResult entityhitresult = ProjectileUtil.getEntityHitResult(
                localPlayer, from, to, new AABB(from, to),
                entity -> !entity.isSpectator() && entity.isPickable(), reach);
            if (entityhitresult != null && minecraft.gameMode != null) {
                minecraft.gameMode.attack(localPlayer, entityhitresult.getEntity());
            }
            localPlayer.resetAttackStrengthTicker();
            localPlayer.swing(InteractionHand.MAIN_HAND);
        }
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.auto_attack");
}
