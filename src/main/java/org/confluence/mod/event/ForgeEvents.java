package org.confluence.mod.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.effect.ManaIssueEffect;
import org.confluence.mod.entity.FallingStarItemEntity;
import org.confluence.mod.item.curio.combat.HoneyComb;
import org.confluence.mod.item.curio.combat.IPoisonInvulnerable;
import org.confluence.mod.mana.ManaProvider;
import org.confluence.mod.util.CuriosUtils;
import org.confluence.mod.util.PlayerUtils;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {
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
        FallingStarItemEntity.summon(serverLevel);
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
        oldPlayer.getCapability(ManaProvider.MANA_CAPABILITY).ifPresent(old -> event.getEntity().getCapability(ManaProvider.MANA_CAPABILITY).ifPresent(neo -> neo.copyFrom(old)));
    }

    @SubscribeEvent
    public static void livingHurt(LivingHurtEvent event) {
        LivingEntity living = event.getEntity();
        if (living.level().isClientSide) return;
        ServerLevel level = (ServerLevel) living.level();
        RandomSource random = level.random;
        float amount = event.getAmount();

        amount *= ManaIssueEffect.apply(event.getSource());
        HoneyComb.apply(living, random);

        event.setAmount(amount * (random.nextInt(80, 121) / 100.0F));
    }

    @SubscribeEvent
    public static void effectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEffectInstance().getEffect() == MobEffects.POISON && CuriosUtils.hasCurio(event.getEntity(), IPoisonInvulnerable.class)) {
            event.setResult(Event.Result.DENY);
        }
    }
}
