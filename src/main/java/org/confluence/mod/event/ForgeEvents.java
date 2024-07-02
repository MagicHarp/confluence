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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.functional.mechanical.NetworkService;
import org.confluence.mod.block.functional.mechanical.PathService;
import org.confluence.mod.block.natural.LogBlocks;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.command.ConfluenceCommand;
import org.confluence.mod.command.ConfluenceData;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.effect.beneficial.ArcheryEffect;
import org.confluence.mod.effect.beneficial.ThornsEffect;
import org.confluence.mod.effect.harmful.BleedingEffect;
import org.confluence.mod.effect.harmful.FrostburnEffect;
import org.confluence.mod.effect.harmful.ManaSicknessEffect;
import org.confluence.mod.entity.FallingStarItemEntity;
import org.confluence.mod.entity.demoneye.DemonEye;
import org.confluence.mod.entity.demoneye.DemonEyeVariant;
import org.confluence.mod.entity.slime.BaseSlime;
import org.confluence.mod.entity.slime.BlackSlime;
import org.confluence.mod.item.curio.HealthAndMana.MagicCuffs;
import org.confluence.mod.item.curio.combat.*;
import org.confluence.mod.item.curio.expert.BrainOfConfusion;
import org.confluence.mod.item.curio.expert.WormScarf;
import org.confluence.mod.item.curio.informational.IDPSMeter;
import org.confluence.mod.item.curio.movement.IFallResistance;
import org.confluence.mod.item.mana.IManaWeapon;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModDamageTypes;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.EntityKilledPacketS2C;
import org.confluence.mod.util.ModUtils;
import org.confluence.mod.util.PlayerUtils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ForgeEvents {
    @SubscribeEvent
    public static void serverAboutToStart(ServerAboutToStartEvent event) {
        PathService.INSTANCE.onServerStart();
        NetworkService.INSTANCE.onServerStart();
    }

    @SubscribeEvent
    public static void serverStop(ServerStoppedEvent event) {
        PathService.INSTANCE.onServerStop();
        NetworkService.INSTANCE.onServerStop();
    }

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        ConfluenceCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            boolean isServerNotFake = PlayerUtils.isServerNotFake(player);
            if (isServerNotFake && !player.getCapability(ManaProvider.CAPABILITY).isPresent()) {
                event.addCapability(new ResourceLocation(Confluence.MODID, "mana"), new ManaProvider());
            }
            if ((isServerNotFake || player.isLocalPlayer()) && !player.getCapability(AbilityProvider.CAPABILITY).isPresent()) {
                event.addCapability(new ResourceLocation(Confluence.MODID, "ability"), new AbilityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void levelTick(TickEvent.LevelTickEvent event) {
        if (event.side == LogicalSide.CLIENT || event.phase == TickEvent.Phase.START) return;

        ServerLevel serverLevel = (ServerLevel) event.level;
        FallingStarItemEntity.summon(serverLevel);
        PathService.INSTANCE.pathFindingTick();
        int dayTime = (int) (serverLevel.getDayTime() % 24000);
        RandomSource random = serverLevel.random;

        if (dayTime == 1) {
            float factorX = (random.nextBoolean() ? 1 : -1) * random.nextFloat();
            float factorZ = (random.nextBoolean() ? 1 : -1) * random.nextFloat();
            ConfluenceData.get(serverLevel).setWindSpeed(factorX, factorZ);
        } else if (dayTime == 6001) {
            if (random.nextFloat() < 0.2F) {
                ConfluenceData.get(serverLevel).setMoonSpecific(random.nextInt(11)); // 0 ~ 10
            } else {
                ConfluenceData.get(serverLevel).setMoonSpecific(-1);
            }
        } else if (dayTime == 12001 && serverLevel.getMoonPhase() != 4 && random.nextFloat() < 0.1111F &&
            serverLevel.players().stream().anyMatch(serverPlayer -> serverPlayer.getMaxHealth() >= 24.0F)
        ) {
            serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("event.confluence.blood_moon").withStyle(ChatFormatting.RED), false);
            ConfluenceData.get(serverLevel).setMoonSpecific(11);
        }
    }

    @SubscribeEvent
    public static void livingHurt(LivingHurtEvent event) {
        LivingEntity living = event.getEntity();
        if (living.level().isClientSide) return;
        DamageSource damageSource = event.getSource();
        if (damageSource.is(DamageTypes.FELL_OUT_OF_WORLD)) return;

        if (damageSource.is(ModDamageTypes.BOULDER) && living.getType().is(Tags.EntityTypes.BOSSES)) {
            event.setCanceled(true);
            return;
        }

        RandomSource random = living.level().random;
        float amount = event.getAmount();

        IHoneycomb.apply(living, random);
        IStarCloak.apply(living, random);
        PanicNecklace.apply(living);
        ThornsEffect.apply(living, damageSource.getEntity(), amount);
        MagicCuffs.apply(living, damageSource, amount);

        amount = IManaWeapon.apply(damageSource, amount);
        amount = IProjectileAttack.apply(damageSource, amount);
        amount = ArcheryEffect.apply(living, damageSource, amount);
        amount = ManaSicknessEffect.apply(damageSource, amount);
        amount = PaladinsShield.apply(living, damageSource, amount);
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

            if (ModConfigs.DROP_MONEY.get() && living instanceof Enemy) {
                Level level = living.level();
                AttributeInstance attack = living.getAttribute(Attributes.ATTACK_DAMAGE);
                AttributeInstance armor = living.getAttribute(Attributes.ARMOR);
                double healthFactor = living.getMaxHealth() * 0.05;
                double attackFactor = attack == null ? 0.0 : attack.getValue() * 0.25;
                double armorFactor = armor == null ? 0.45 : (armor.getValue() + 1.0) * 0.45;
                double difficultyFactor = switch (level.getDifficulty()) {
                    case PEACEFUL -> 0.5;
                    case EASY -> 0.75;
                    case NORMAL -> 1.0;
                    case HARD -> 1.5;
                };
                int amount = (int) Math.min(Math.round((healthFactor + attackFactor + armorFactor) * difficultyFactor), 7290L);
                ModUtils.dropMoney(amount, living.getX(), living.getEyeY() - 0.3, living.getZ(), level);
            }
        }
        if (!living.level().isClientSide) {
            BaseSlime.dropColoredGel(living);
            BlackSlime.dropColoredGel(living);
        }
    }

    @SubscribeEvent
    public static void livingChangeTarget(LivingChangeTargetEvent event) {
        LivingEntity self = event.getEntity();
        if (!(self instanceof Enemy)) return;
        double range = self.getAttributeValue(Attributes.FOLLOW_RANGE);
        double rangeSqr = range * range;
        self.level().players().stream()
            .filter(player -> player.distanceToSqr(self) < rangeSqr && self.canAttack(self))
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
        LivingEntity living = event.getEntity();
        Consumer<Boolean> consumer = event::setCanceled;
        BleedingEffect.apply(living, consumer);
        FrostburnEffect.apply(living, consumer);
        if (event.isCanceled() || !(living instanceof Player player)) return;
        player.getCapability(AbilityProvider.CAPABILITY).ifPresent(playerAbility -> {
            if (playerAbility.isVitalCrystalUsed()) {
                event.setAmount(event.getAmount() * 1.2F);
            }
        });
    }

    @SubscribeEvent
    public static void mobFinalizeSpawn(MobSpawnEvent.FinalizeSpawn event) {
        Mob mob = event.getEntity();
        RandomSource randomSource = mob.getRandom();
        if (mob instanceof DemonEye demonEye) {
            demonEye.setVariant(DemonEyeVariant.random(randomSource));
        } else if (mob instanceof BlackSlime blackSlime) {
            blackSlime.finalizeSpawn(randomSource, event.getDifficulty());
        }
//        if (mob instanceof Enemy && event.getSpawnType() != MobSpawnType.SPAWNER && mob.level() instanceof ServerLevel serverLevel) {
//            for (int i = 0; i < 4; i++) {
//                mob.getType().spawn(serverLevel, mob.blockPosition(), MobSpawnType.SPAWNER);
//            }
//        }
    }

    @SubscribeEvent
    public static void livingBreathe(LivingBreatheEvent event) {
        if (event.canBreathe()) return;
        if (event.getEntity().hasEffect(ModEffects.SHIMMER.get())) {
            event.setCanBreathe(true);
        }
    }

    @SubscribeEvent
    public static void blockToolModification(BlockEvent.BlockToolModificationEvent event) {
        if (event.getToolAction() == ToolActions.AXE_STRIP) {
            BlockState originalState = event.getState();
            Block block = LogBlocks.WRAPPED_STRIP.get(originalState.getBlock());
            if (block != null) {
                event.setFinalState(block.defaultBlockState().setValue(RotatedPillarBlock.AXIS, originalState.getValue(RotatedPillarBlock.AXIS)));
            }
        }
    }
}
