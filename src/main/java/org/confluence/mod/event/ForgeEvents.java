package org.confluence.mod.event;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.entity.FallingStarItemEntity;
import org.confluence.mod.item.magic.IMagicAttack;
import org.confluence.mod.mana.ManaProvider;
import org.confluence.mod.util.PlayerUtils;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {
    public static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (player.getCapability(ManaProvider.MANA_CAPABILITY).isPresent()) return;
            event.addCapability(new ResourceLocation(Confluence.MODID, "mana"), new ManaProvider());
        }
    }

    @SubscribeEvent
    public static void levelTick(TickEvent.LevelTickEvent event) {
        if (event.side == LogicalSide.CLIENT || event.phase == TickEvent.Phase.START) return;

        ServerLevel serverLevel = (ServerLevel) event.level;
        if (serverLevel.dimension().equals(Level.OVERWORLD) && serverLevel.getDayTime() > 12000L && serverLevel.getGameTime() % 600 == 0) {
            for (ServerPlayer serverPlayer : serverLevel.players()) {
                int distance = serverLevel.getServer().getScaledTrackingDistance(1);
                int offsetX = (RANDOM.nextFloat() < 0.5F ? 1 : -1) * RANDOM.nextInt(distance);
                int offsetZ = (RANDOM.nextFloat() < 0.5F ? 1 : -1) * RANDOM.nextInt(distance);
                BlockPos pos = serverPlayer.getOnPos().offset(offsetX, 0, offsetZ);
                if (serverLevel.isLoaded(pos)) {
                    serverLevel.addFreshEntity(new FallingStarItemEntity(serverLevel, pos.atY(256).getCenter()));
                }
            }
        }
    }

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
        Player oldPlayer = event.getOriginal();
        oldPlayer.revive();
        oldPlayer.getCapability(ManaProvider.MANA_CAPABILITY)
            .ifPresent(old -> event.getEntity().getCapability(ManaProvider.MANA_CAPABILITY)
                .ifPresent(neo -> neo.copyFrom(old))
            );
    }

    @SubscribeEvent
    public static void livingHurt(LivingHurtEvent event) {
        float amount = event.getAmount();
        if (event.getSource().getEntity() instanceof Player player) {
            MobEffectInstance manaIssue = player.getEffect(ModEffects.MANA_ISSUE.get());
            boolean isMagic = event.getSource().is(DamageTypes.MAGIC) || player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof IMagicAttack;
            if (manaIssue != null && isMagic) {
                int duration = manaIssue.getDuration();
                if (duration == -1) {
                    amount *= 0.5F;
                } else if (duration <= 100) {
                    amount *= 0.75F;
                } else {
                    amount *= 0.75F - 0.05F * Math.round((duration - 100) / 20.0F);
                }
            }
        }
        event.setAmount(amount * RANDOM.nextFloat(0.8F, 1.2F));
    }
}
