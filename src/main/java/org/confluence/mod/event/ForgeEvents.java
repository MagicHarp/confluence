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
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
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
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.command.ConfluenceCommand;
import org.confluence.mod.command.ConfluenceData;
import org.confluence.mod.effect.beneficial.ThornsEffect;
import org.confluence.mod.effect.harmful.BleedingEffect;
import org.confluence.mod.effect.harmful.ManaIssueEffect;
import org.confluence.mod.entity.FallingStarItemEntity;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.curio.HealthAndMana.MagicCuffs;
import org.confluence.mod.item.curio.combat.*;
import org.confluence.mod.item.curio.expert.BrainOfConfusion;
import org.confluence.mod.item.curio.expert.RoyalGel;
import org.confluence.mod.item.curio.expert.WormScarf;
import org.confluence.mod.item.curio.informational.IDPSMeter;
import org.confluence.mod.item.curio.movement.IFallResistance;
import org.confluence.mod.item.mana.IManaWeapon;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.EntityKilledPacketS2C;
import org.confluence.mod.util.ModUtils;

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

            if (player.getCapability(AbilityProvider.CAPABILITY).isPresent()) return;
            event.addCapability(new ResourceLocation(Confluence.MODID, "ability"), new AbilityProvider());
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
            serverLevel.players().forEach(serverPlayer -> serverPlayer
                .sendSystemMessage(Component.translatable("event.confluence.blood_moon").withStyle(ChatFormatting.RED))
            );
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

        amount = IManaWeapon.apply(damageSource, amount);
        amount = ManaIssueEffect.apply(damageSource, amount);
        amount = PaladinsShield.apply(living, amount);
        amount = FrozenTurtleShell.apply(living, amount);
        amount = ILavaHurtReduce.apply(living, damageSource, amount);
        amount = IFallResistance.apply(living, damageSource, amount);
        amount = WormScarf.apply(living, amount);
        amount = BrainOfConfusion.apply(living, random, damageSource, amount);

        amount *= ModUtils.nextFloat(random, 0.8F, 1.2F);
        IDPSMeter.sendMsg(amount, damageSource.getEntity());
        event.setAmount(amount);
    }

    @SubscribeEvent
    public static void livingDeath(LivingDeathEvent event) {
        LivingEntity living = event.getEntity();
        if (event.getSource().getEntity() instanceof ServerPlayer serverPlayer) {
            EntityType<?> entityType = living.getType();
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new EntityKilledPacketS2C(
                    serverPlayer.getStats().getValue(Stats.ENTITY_KILLED.get(entityType)),
                    ForgeRegistries.ENTITY_TYPES.getKey(entityType)
                )
            );

            if (ModConfigs.dropsMoney && living instanceof Enemy) {
                Level level = living.level();
                AttributeInstance attack = living.getAttribute(Attributes.ATTACK_DAMAGE);
                AttributeInstance armor = living.getAttribute(Attributes.ARMOR);
                double healthFactor = living.getMaxHealth() * 0.05;
                double attackFactor = attack == null ? 0.0 : attack.getBaseValue() * 0.25;
                double armorFactor = armor == null ? 0.45 : (armor.getBaseValue() + 1.0) * 0.45;
                double difficultyFactor = switch (level.getDifficulty()) {
                    case PEACEFUL -> 0.0;
                    case EASY -> 0.75;
                    case NORMAL -> 1.0;
                    case HARD -> 1.5;
                };
                int amount = (int) Math.min(Math.round((healthFactor + attackFactor + armorFactor) * difficultyFactor), 7290L);
                int copper_count = amount % 9;
                int i = ((amount - copper_count) / 9);
                int silver_count = i % 9;
                int j = ((i - silver_count) / 9);
                int golden_count = j % 9;
                int k = (j - golden_count) / 9;
                int platinum_count = k % 9;
                double x = living.getX();
                double y = living.getEyeY() - 0.3;
                double z = living.getZ();
                ModUtils.createItemEntity(ModItems.COPPER_COIN.get(), copper_count, x, y, z, level);
                ModUtils.createItemEntity(ModItems.SILVER_COIN.get(), silver_count, x, y, z, level);
                ModUtils.createItemEntity(ModItems.GOLDEN_COIN.get(), golden_count, x, y, z, level);
                ModUtils.createItemEntity(ModItems.PLATINUM_COIN.get(), platinum_count, x, y, z, level);
            }
        }
    }

    @SubscribeEvent
    public static void livingChangeTarget(LivingChangeTargetEvent event) {
        LivingEntity self = event.getEntity();
        LivingEntity original = event.getOriginalTarget();
        if (RoyalGel.apply(self, original)) {
            event.setNewTarget(null);
            return;
        }

        double range = self.getAttributeValue(Attributes.FOLLOW_RANGE);
        self.level().players().stream()
            .filter(player -> player.distanceTo(self) < range)
            .max((playerA, playerB) -> {
                AtomicInteger atomic = new AtomicInteger();
                playerA.getCapability(AbilityProvider.CAPABILITY).ifPresent(abilityA ->
                    playerB.getCapability(AbilityProvider.CAPABILITY).ifPresent(abilityB ->
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
