package org.confluence.mod.event;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.effect.beneficial.GravitationEffect;
import org.confluence.mod.item.curio.combat.IAutoAttack;
import org.confluence.mod.item.curio.combat.ICriticalHit;
import org.confluence.mod.item.curio.combat.IFireAttack;
import org.confluence.mod.item.curio.construction.AncientChisel;
import org.confluence.mod.item.curio.movement.IMayFly;
import org.confluence.mod.item.curio.movement.IMultiJump;
import org.confluence.mod.mixin.LocalPlayerAccessor;
import org.confluence.mod.network.s2c.InfoCurioCheckPacketS2C;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class PlayerEvents {
    @SubscribeEvent
    public static void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            InfoCurioCheckPacketS2C.send(serverPlayer, serverPlayer.getInventory());
        }
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (event.side != LogicalSide.CLIENT) return;
            LocalPlayer localPlayer = (LocalPlayer) event.player;
            if (GravitationEffect.isShouldRot() && localPlayer.onGround() && localPlayer.isCrouching() && !localPlayer.isShiftKeyDown()) {
                localPlayer.move(MoverType.SELF, new Vec3(0.0, -0.3000001, 0.0));
                localPlayer.setPose(Pose.STANDING);
                ((LocalPlayerAccessor) localPlayer).setCrouching(false);
            }
        }
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;
        Player oldPlayer = event.getOriginal();
        Player neoPlayer = event.getEntity();
        oldPlayer.revive();

        oldPlayer.getCapability(AbilityProvider.CAPABILITY).ifPresent(old -> neoPlayer.getCapability(AbilityProvider.CAPABILITY).ifPresent(neo -> neo.copyFrom(old)));

        if (neoPlayer instanceof ServerPlayer serverPlayer) {
            IMultiJump.sendMsg(serverPlayer);
            IMayFly.sendMsg(serverPlayer);
            IAutoAttack.sendMsg(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void attackEntity(AttackEntityEvent event) {
        IFireAttack.apply(event.getEntity(), event.getTarget());
    }

    @SubscribeEvent
    public static void criticalHit(CriticalHitEvent event) {
        ICriticalHit.apply(event);
    }

    @SubscribeEvent
    public static void breakSpeed(PlayerEvent.BreakSpeed event) {
        float speed = event.getOriginalSpeed();
        speed = AncientChisel.apply(event.getEntity(), speed);
        event.setNewSpeed(speed);
    }
}
