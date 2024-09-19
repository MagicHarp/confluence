package org.confluence.mod.event;

import com.mojang.datafixers.util.Function3;
import net.bettercombat.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.crafting.AltarBlock;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.client.handler.GravitationHandler;
import org.confluence.mod.item.IRangePickup;
import org.confluence.mod.item.common.LifeCrystal;
import org.confluence.mod.item.common.LifeFruit;
import org.confluence.mod.item.common.PlayerAbilityItem;
import org.confluence.mod.item.curio.combat.IFireAttack;
import org.confluence.mod.item.curio.miscellaneous.LuckyCoin;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModTags;
import org.confluence.mod.network.s2c.InfoCurioCheckPacketS2C;
import org.confluence.mod.util.CuriosUtils;
import org.confluence.mod.util.PlayerUtils;

import java.util.Optional;

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
            PlayerUtils.resetClientPacket(serverPlayer);
            InfoCurioCheckPacketS2C.send(serverPlayer, serverPlayer.getInventory());
        }
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (event.player.isLocalPlayer()) {
                GravitationHandler.unCrouching(event.player);
            }
        } else {
            Player player = event.player;
            IRangePickup.Star.apply(player);
            IRangePickup.Heart.apply(player);
            IRangePickup.Coin.apply(player);
            if (PlayerUtils.isServerNotFake(player)) {
                ServerPlayer serverPlayer = (ServerPlayer) player;
                PlayerUtils.regenerateMana(serverPlayer);
                if (serverPlayer.level().getGameTime() % 200 == 0) {
                    // 每十秒向周围玩家共享一次信息配饰
                    InfoCurioCheckPacketS2C.sendToOthers(serverPlayer);
                }
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
        oldPlayer.getCapability(AbilityProvider.CAPABILITY).ifPresent(old -> neoPlayer.getCapability(AbilityProvider.CAPABILITY).ifPresent(neo -> {
            neo.copyFrom(old);
            LifeCrystal.applyModifier(neoPlayer, neo);
            LifeFruit.applyModifier(neoPlayer, neo);
            PlayerAbilityItem.AegisApple.applyModifier(neoPlayer, neo);
            PlayerAbilityItem.GalaxyPearl.applyModifier(neoPlayer, neo);
            PlayerAbilityItem.Ambrosia.applyModifier(neoPlayer, neo);
        }));

        if (PlayerUtils.isServerNotFake(neoPlayer)) {
            PlayerUtils.resetClientPacket((ServerPlayer) neoPlayer);
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
        if (ModAttributes.hasCustomAttribute(ModAttributes.CRIT_CHANCE.get())) return;
        Player player = event.getEntity();
        if (!event.isVanillaCritical()) {
            double chance = player.getAttributeValue(ModAttributes.CRIT_CHANCE.get());
            if (player.level().random.nextFloat() < chance) {
                event.setDamageModifier(1.5F);
                event.setResult(Event.Result.ALLOW);
            }
        }
    }

    @SubscribeEvent
    public static void breakSpeed(PlayerEvent.BreakSpeed event) {
        BlockState blockState = event.getState();
        Player player = event.getEntity();
        if (blockState.is(ModTags.Blocks.NEEDS_NON_VANILLA_LEVEL)) {
            int tier = 0;
            if (player.getMainHandItem().getItem() instanceof TieredItem tieredItem) {
                tier = tieredItem.getTier().getLevel();
            }
            if ((tier < 8 && blockState.is(ModTags.Blocks.NEEDS_8_LEVEL)) ||
                (tier < 7 && blockState.is(ModTags.Blocks.NEEDS_7_LEVEL)) ||
                (tier < 6 && blockState.is(ModTags.Blocks.NEEDS_6_LEVEL)) ||
                (tier < 5 && blockState.is(ModTags.Blocks.NEEDS_5_LEVEL)) ||
                (tier < 4 && blockState.is(ModTags.Blocks.NEEDS_4_LEVEL))
            ) {
                event.setNewSpeed(0.0F);
                return;
            }
        }

        if (ModAttributes.hasCustomAttribute(ModAttributes.MINING_SPEED.get())) return;
        AttributeInstance attributeInstance = player.getAttribute(ModAttributes.MINING_SPEED.get());
        if (attributeInstance == null) return;
        event.setNewSpeed(event.getNewSpeed() * (float) attributeInstance.getValue());
    }

    @SubscribeEvent
    public static void rightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntity().isCrouching() || event.getItemStack().is(ModTags.Items.MINECART)) return;
        Level level = event.getLevel();
        BlockPos blockPos = event.getPos();
        BlockState blockState = level.getBlockState(blockPos);
        if (blockState.getBlock() instanceof BaseRailBlock railBlock) {
            Optional<ItemStack> optionalItemStack = CuriosUtils.getSlot(event.getEntity(), "minecart", 0);
            if (optionalItemStack.isEmpty()) return;
            event.getEntity().swing(InteractionHand.MAIN_HAND);
            if (!level.isClientSide) {
                ItemStack itemStack = optionalItemStack.get();
                Item item = itemStack.getItem();
                AbstractMinecart abstractMinecart = null;
                Function3<Level, BlockPos, Double, AbstractMinecart> factory = Confluence.CURIO_MINECART.get(item);
                if (factory != null) {
                    boolean ascending = railBlock.getRailDirection(blockState, level, blockPos, null).isAscending();
                    abstractMinecart = factory.apply(level, blockPos, ascending ? 0.5 : 0.0);
                }
                if (abstractMinecart != null) {
                    itemStack.shrink(1);
                    level.addFreshEntity(abstractMinecart);
                    event.getEntity().startRiding(abstractMinecart, true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void leftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();

        AltarBlock.onLeftClick(level.getBlockState(pos), level, pos, event.getEntity());
    }



}
