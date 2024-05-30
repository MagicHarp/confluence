package org.confluence.mod.event;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.functional.ActuatorsBlock;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.effect.beneficial.GravitationEffect;
import org.confluence.mod.item.IRangePickup;
import org.confluence.mod.item.common.LifeCrystal;
import org.confluence.mod.item.common.LifeFruit;
import org.confluence.mod.item.common.PlayerAbilityItem;
import org.confluence.mod.item.curio.combat.IAutoAttack;
import org.confluence.mod.item.curio.combat.ICriticalHit;
import org.confluence.mod.item.curio.combat.IFireAttack;
import org.confluence.mod.item.curio.miscellaneous.LuckyCoin;
import org.confluence.mod.item.curio.movement.IMayFly;
import org.confluence.mod.item.curio.movement.IMultiJump;
import org.confluence.mod.network.s2c.InfoCurioCheckPacketS2C;
import org.confluence.mod.util.PlayerUtils;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class PlayerEvents {
    @SubscribeEvent
    public static void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (PlayerUtils.isServerNotFake(player)) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            PlayerUtils.syncMana2Client(serverPlayer);
            PlayerUtils.syncSavedData(serverPlayer);
            PlayerUtils.syncAdvancements(serverPlayer);
            InfoCurioCheckPacketS2C.send(serverPlayer, serverPlayer.getInventory());
        }
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (event.player instanceof LocalPlayer localPlayer) {
                GravitationEffect.unCrouching(localPlayer);
            }
        } else {
            Player player = event.player;
            IRangePickup.Star.apply(player);
            IRangePickup.Heart.apply(player);
            IRangePickup.Coin.apply(player);
            IRangePickup.Drops.apply(player);
            if (PlayerUtils.isServerNotFake(player)) {
                PlayerUtils.regenerateMana((ServerPlayer) player);
            }
        }
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;
        Player oldPlayer = event.getOriginal();
        Player neoPlayer = event.getEntity();
        oldPlayer.revive();

        oldPlayer.getCapability(ManaProvider.CAPABILITY).ifPresent(old -> neoPlayer.getCapability(ManaProvider.CAPABILITY).ifPresent(neo -> neo.copyFrom(old)));
        oldPlayer.getCapability(AbilityProvider.CAPABILITY).ifPresent(old -> neoPlayer.getCapability(AbilityProvider.CAPABILITY)
            .ifPresent(neo -> {
                neo.copyFrom(old);
                LifeCrystal.applyModifier(neoPlayer, neo);
                LifeFruit.applyModifier(neoPlayer, neo);
                PlayerAbilityItem.AegisApple.applyModifier(neoPlayer, neo);
                PlayerAbilityItem.GalaxyPearl.applyModifier(neoPlayer, neo);
            }));

        if (PlayerUtils.isServerNotFake(neoPlayer)) {
            ServerPlayer serverPlayer = (ServerPlayer) neoPlayer;
            IMultiJump.sendMsg(serverPlayer);
            IMayFly.sendMsg(serverPlayer);
            IAutoAttack.sendMsg(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void attackEntity(AttackEntityEvent event) {
        Player player = event.getEntity();
        if (PlayerUtils.isServerNotFake(player)) {
            Entity target = event.getTarget();
            IFireAttack.apply(player, target);
            LuckyCoin.apply(player, target);
        }
    }

    @SubscribeEvent
    public static void criticalHit(CriticalHitEvent event) {
        ICriticalHit.apply(event);
    }

    @SubscribeEvent
    public static void breakSpeed(PlayerEvent.BreakSpeed event) {
        MutableFloat speed = new MutableFloat(event.getOriginalSpeed());
        event.getEntity().getCapability(AbilityProvider.CAPABILITY).ifPresent(playerAbility -> {
            float value = speed.floatValue();
            value *= (1.0F + playerAbility.getBreakSpeedBonus());
            if (playerAbility.isAmbrosiaUsed()) value *= 1.05F;
            speed.setValue(value);
        });
        event.setNewSpeed(speed.floatValue());
    }

    @SubscribeEvent
    public static void rightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ActuatorsBlock.blockItemPlace(event);
    }
}
