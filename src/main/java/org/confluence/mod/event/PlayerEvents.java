package org.confluence.mod.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.capability.curio.AbilityProvider;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.item.curio.combat.ICriticalHit;
import org.confluence.mod.item.curio.combat.IFireAttack;
import org.confluence.mod.item.curio.movement.IMayFly;
import org.confluence.mod.item.curio.movement.IMultiJump;
import org.confluence.mod.util.PlayerUtils;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEvents {
    @SubscribeEvent
    public static void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            PlayerUtils.syncMana2Client(serverPlayer);
            PlayerUtils.syncAdvancements(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.CLIENT || event.phase == TickEvent.Phase.START) return;

        ServerPlayer serverPlayer = (ServerPlayer) event.player;
        PlayerUtils.regenerateMana(serverPlayer);
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        Player oldPlayer = event.getOriginal();
        Player neoPlayer = event.getEntity();
        oldPlayer.revive();
        oldPlayer.getCapability(ManaProvider.MANA_CAPABILITY).ifPresent(old ->
            neoPlayer.getCapability(ManaProvider.MANA_CAPABILITY).ifPresent(neo -> neo.copyFrom(old)));
        oldPlayer.getCapability(AbilityProvider.ABILITY_CAPABILITY).ifPresent(old ->
            neoPlayer.getCapability(AbilityProvider.ABILITY_CAPABILITY).ifPresent(neo -> neo.copyFrom(old)));

        if (neoPlayer instanceof ServerPlayer serverPlayer) {
            IMultiJump.sendMsg(serverPlayer);
            IMayFly.sendMsg(serverPlayer);
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
}
