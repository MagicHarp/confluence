package org.confluence.mod.event;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.Confluence;
import org.confluence.mod.capability.ability.PlayerAbilityProvider;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.command.ConfluenceCommand;
import org.confluence.mod.command.ConfluenceData;
import org.confluence.mod.effect.BeneficialEffect.ThornsEffect;
import org.confluence.mod.effect.HarmfulEffect.BleedingEffect;
import org.confluence.mod.effect.HarmfulEffect.ManaIssueEffect;
import org.confluence.mod.entity.FallingStarItemEntity;
import org.confluence.mod.item.curio.HealthAndMana.MagicCuffs;
import org.confluence.mod.item.curio.combat.*;
import org.confluence.mod.item.curio.informational.IDPSMeter;
import org.confluence.mod.item.curio.movement.IFallResistance;
import org.confluence.mod.item.magic.IMagicAttack;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.EntityKilledPacketS2C;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ForgeEvents {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        ConfluenceCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (player.getCapability(ManaProvider.CAPABILITY).isPresent()) return;
            event.addCapability(new ResourceLocation(Confluence.MODID, "mana"), new ManaProvider());

            if (player.getCapability(PlayerAbilityProvider.CAPABILITY).isPresent()) return;
            event.addCapability(new ResourceLocation(Confluence.MODID, "ability"), new PlayerAbilityProvider());
        }
    }

    @SubscribeEvent
    public static void levelTick(TickEvent.LevelTickEvent event) {
        if (event.side == LogicalSide.CLIENT || event.phase == TickEvent.Phase.START) return;

        ServerLevel serverLevel = (ServerLevel) event.level;
        FallingStarItemEntity.summon(serverLevel);
        int dayTime = (int) (serverLevel.getDayTime() % 24000);
        RandomSource random = serverLevel.random;

        if (dayTime == 0) {
            float factorX = (random.nextBoolean() ? 1 : -1) * random.nextFloat();
            float factorZ = (random.nextBoolean() ? 1 : -1) * random.nextFloat();
            ConfluenceData.get(serverLevel).setWindSpeed(factorX, factorZ);
        } else if (dayTime == 6000) {
            if (random.nextFloat() < 0.2F) {
                ConfluenceData.get(serverLevel).setMoonSpecific(random.nextInt(11)); // 0 ~ 10
            } else {
                ConfluenceData.get(serverLevel).setMoonSpecific(-1);
            }
        } else if (dayTime == 12000 && serverLevel.getMoonPhase() != 4 && random.nextFloat() < 0.1111F &&
            serverLevel.players().stream().anyMatch(serverPlayer -> serverPlayer.getMaxHealth() >= 24)
        ) {
            serverLevel.getServer().sendSystemMessage(Component.translatable("event.confluence.blood_moon").withStyle(ChatFormatting.RED));
            ConfluenceData.get(serverLevel).setMoonSpecific(11);
        }
    }

    @SubscribeEvent
    public static void livingHurt(LivingHurtEvent event) {
        LivingEntity living = event.getEntity();
        if (living.level().isClientSide) return;
        DamageSource damageSource = event.getSource();
        if (damageSource.is(DamageTypes.FELL_OUT_OF_WORLD)) return;
        RandomSource random = living.level().random;
        float amount = event.getAmount();

        HoneyComb.apply(living, random);
        PanicNecklace.apply(living);
        ThornsEffect.apply(living, damageSource.getEntity(), amount);
        MagicCuffs.apply(living, damageSource, amount);

        amount = IMagicAttack.apply(damageSource, amount);
        amount = ManaIssueEffect.apply(damageSource, amount);
        amount = PaladinsShield.apply(living, amount);
        amount = FrozenTurtleShell.apply(living, amount);
        amount = ILavaHurtReduce.apply(living, damageSource, amount);
        amount = IFallResistance.apply(living, damageSource, amount);

        amount *= (random.nextInt(80, 121) / 100.0F);
        IDPSMeter.sendMsg(amount, damageSource.getEntity());
        event.setAmount(amount);
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
            old.getCapability(PlayerAbilityProvider.CAPABILITY).ifPresent(oldAbility ->
                neo.getCapability(PlayerAbilityProvider.CAPABILITY).ifPresent(neoAbility -> {
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
                playerA.getCapability(PlayerAbilityProvider.CAPABILITY).ifPresent(abilityA ->
                    playerB.getCapability(PlayerAbilityProvider.CAPABILITY).ifPresent(abilityB ->
                        atomic.set(abilityA.getAggro() - abilityB.getAggro())
                    )
                );
                return atomic.get();
            }).ifPresent(event::setNewTarget);
    }

    @SubscribeEvent
    public static void livingHeal(LivingHealEvent event) {
        BleedingEffect.apply(event.getEntity(), event);
    }
}
