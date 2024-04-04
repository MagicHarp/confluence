package org.confluence.mod.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.Confluence;
import org.confluence.mod.capability.curio.AbilityProvider;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.command.ConfluenceCommand;
import org.confluence.mod.effect.ManaIssueEffect;
import org.confluence.mod.entity.FallingStarItemEntity;
import org.confluence.mod.item.curio.combat.*;
import org.confluence.mod.item.curio.movement.IFallResistance;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.EntityKilledPacketS2C;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        ConfluenceCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (player.getCapability(ManaProvider.MANA_CAPABILITY).isPresent()) return;
            event.addCapability(new ResourceLocation(Confluence.MODID, "mana"), new ManaProvider());

            if (player.getCapability(AbilityProvider.ABILITY_CAPABILITY).isPresent()) return;
            event.addCapability(new ResourceLocation(Confluence.MODID, "ability"), new AbilityProvider());
        }
    }

    @SubscribeEvent
    public static void levelTick(TickEvent.LevelTickEvent event) {
        if (event.side == LogicalSide.CLIENT || event.phase == TickEvent.Phase.START) return;

        ServerLevel serverLevel = (ServerLevel) event.level;
        FallingStarItemEntity.summon(serverLevel);
    }

    @SubscribeEvent
    public static void livingHurt(LivingHurtEvent event) {
        LivingEntity living = event.getEntity();
        if (living.level().isClientSide) return;
        DamageSource damageSource = event.getSource();
        if (damageSource.is(DamageTypes.FELL_OUT_OF_WORLD)) return;
        RandomSource random = living.level().random;

        HoneyComb.apply(living, random);
        PanicNecklace.apply(living);
        if (IHurtEvasion.apply(living, random) || IFireImmune.apply(living, damageSource)) {
            event.setCanceled(true);
            return;
        }
        float amount = event.getAmount();
        amount = ManaIssueEffect.apply(damageSource, amount);
        amount = PaladinsShield.apply(living, amount);
        amount = ILavaHurtReduce.apply(living, damageSource, amount);
        amount = IFallResistance.apply(living, damageSource, amount);

        event.setAmount(amount * (random.nextInt(80, 121) / 100.0F));
    }

    @SubscribeEvent
    public static void livingDeath(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer serverPlayer) {
            EntityType<?> entityType = event.getEntity().getType();
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new EntityKilledPacketS2C(
                    serverPlayer.getStats().getValue(Stats.ENTITY_KILLED.get(entityType)),
                    ForgeRegistries.ENTITY_TYPES.getKey(entityType)
                )
            );
        }
    }

    @SubscribeEvent
    public static void livingChangeTarget(LivingChangeTargetEvent event) {
        LivingEntity old = event.getOriginalTarget();
        if (old != null) {
            LivingEntity neo = event.getNewTarget();
            AtomicBoolean bothHas = new AtomicBoolean();
            old.getCapability(AbilityProvider.ABILITY_CAPABILITY).ifPresent(oldAbility ->
                neo.getCapability(AbilityProvider.ABILITY_CAPABILITY).ifPresent(neoAbility -> {
                    bothHas.set(true);
                    if (oldAbility.getAggro() > neoAbility.getAggro()) {
                        event.setNewTarget(old);
                    }
                })
            );
            if (bothHas.get()) return;
        }

        LivingEntity self = event.getEntity();
        double range = self.getAttributeValue(Attributes.FOLLOW_RANGE);
        self.level().players().stream()
            .filter(player -> player.distanceTo(self) < range)
            .max((playerA, playerB) -> {
                AtomicInteger atomic = new AtomicInteger();
                playerA.getCapability(AbilityProvider.ABILITY_CAPABILITY).ifPresent(abilityA ->
                    playerB.getCapability(AbilityProvider.ABILITY_CAPABILITY).ifPresent(abilityB ->
                        atomic.set(abilityA.getAggro() - abilityB.getAggro())
                    )
                );
                return atomic.get();
            }).ifPresent(event::setNewTarget);
    }
}
