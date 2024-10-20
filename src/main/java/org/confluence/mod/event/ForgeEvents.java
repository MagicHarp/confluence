package org.confluence.mod.event;

import com.xiaohunao.mine_team.common.event.RegisterTeamDataEvent;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.Confluence;
import org.confluence.mod.advancement.ModTriggers;
import org.confluence.mod.block.functional.network.NetworkService;
import org.confluence.mod.block.functional.network.PathService;
import org.confluence.mod.block.natural.LogBlocks;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.client.particle.options.DamageIndicatorOptions;
import org.confluence.mod.command.ConfluenceCommand;
import org.confluence.mod.command.ConfluenceData;
import org.confluence.mod.command.SpecificMoon;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.effect.beneficial.ArcheryEffect;
import org.confluence.mod.effect.beneficial.ThornsEffect;
import org.confluence.mod.effect.harmful.BleedingEffect;
import org.confluence.mod.effect.harmful.FrostburnEffect;
import org.confluence.mod.effect.harmful.ManaSicknessEffect;
import org.confluence.mod.entity.FallingStarItemEntity;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.entity.boss.Boss;
import org.confluence.mod.entity.boss.TerraBossBase;
import org.confluence.mod.entity.monster.demoneye.DemonEye;
import org.confluence.mod.entity.monster.demoneye.DemonEyeVariant;
import org.confluence.mod.entity.projectile.bombs.ScarabBombEntity;
import org.confluence.mod.entity.monster.slime.BaseSlime;
import org.confluence.mod.entity.monster.slime.BlackSlime;
import org.confluence.mod.item.curio.HealthAndMana.MagicCuffs;
import org.confluence.mod.item.curio.combat.*;
import org.confluence.mod.item.curio.expert.BrainOfConfusion;
import org.confluence.mod.item.curio.expert.WormScarf;
import org.confluence.mod.item.curio.informational.IDPSMeter;
import org.confluence.mod.item.curio.movement.IFallResistance;
import org.confluence.mod.item.sword.BloodButchereSword;
import org.confluence.mod.item.sword.BreathingReed;
import org.confluence.mod.item.sword.TentacleMaceSword;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModDamageTypes;
import org.confluence.mod.mixin.accessor.EntityAccessor;
import org.confluence.mod.mixinauxiliary.ILivingEntity;
import org.confluence.mod.mixinauxiliary.Immunity;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.EntityKilledPacketS2C;
import org.confluence.mod.network.s2c.FlushPlayerAbilityPacketS2C;
import org.confluence.mod.util.CuriosUtils;
import org.confluence.mod.util.DelayedTaskExecutor;
import org.confluence.mod.util.ModUtils;
import org.confluence.mod.util.PlayerUtils;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.event.CurioEquipEvent;

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
                event.addCapability(Confluence.asResource("mana"), new ManaProvider());
            }
            if ((isServerNotFake || player.isLocalPlayer()) && !player.getCapability(AbilityProvider.CAPABILITY).isPresent()) {
                event.addCapability(Confluence.asResource("ability"), new AbilityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void levelTick(TickEvent.LevelTickEvent event) {
        if (!(event.level instanceof ServerLevel serverLevel) || event.phase == TickEvent.Phase.START) return;
        PathService.INSTANCE.pathFindingTick();
        if (serverLevel.dimension() != Level.OVERWORLD) return;
        FallingStarItemEntity.summon(serverLevel);
        int dayTime = (int) (serverLevel.getDayTime() % 24000L);
        RandomSource random = serverLevel.random;

        if (dayTime == 1) {
            ConfluenceData data = ConfluenceData.get(serverLevel);
            float factorX = (random.nextBoolean() ? 1 : -1) * random.nextFloat();
            float factorZ = (random.nextBoolean() ? 1 : -1) * random.nextFloat();
            data.setWindSpeed(factorX, factorZ);
            if (data.getSpecificMoon().isBloodyMoon()) {
                data.setSpecificMoon(SpecificMoon.VANILLA);
            }
        } else if (dayTime == 6001) {
            if (random.nextFloat() < 0.2F) {
                ConfluenceData.get(serverLevel).setSpecificMoon(SpecificMoon.getCommonMoon(random, serverLevel.getMoonPhase()));
            } else {
                ConfluenceData.get(serverLevel).setSpecificMoon(SpecificMoon.VANILLA);
            }
        } else if (dayTime == 12001 && serverLevel.getMoonPhase() != 4 && random.nextFloat() < 0.1111F && serverLevel.players().stream().anyMatch(serverPlayer -> serverPlayer.getMaxHealth() >= 24.0F)) {
            serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("event.confluence.blood_moon").withStyle(ChatFormatting.RED), false);
            ConfluenceData.get(serverLevel).setSpecificMoon(SpecificMoon.getBloodyMoon(random.nextBoolean(), serverLevel.getMoonPhase()));
        }
    }

    @SubscribeEvent
    public static void livingHurt(LivingHurtEvent event) {
        LivingEntity living = event.getEntity();
        if (living.level().isClientSide) return;
        DamageSource damageSource = event.getSource();
        if (damageSource.is(DamageTypes.FELL_OUT_OF_WORLD) || damageSource.is(DamageTypes.GENERIC_KILL)) return;

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

        amount = ModAttributes.applyMagicDamage(damageSource, amount);
        amount = ModAttributes.applyRangedDamage(living, damageSource, amount);
        amount = ArcheryEffect.apply(living, damageSource, amount);
        amount = ManaSicknessEffect.apply(damageSource, amount);
        amount = PaladinsShield.apply(living, damageSource, amount);
        amount = FrozenTurtleShell.apply(living, amount);
        amount = ILavaHurtReduce.apply(living, damageSource, amount);
        amount = IFallResistance.apply(living, damageSource, amount);
        amount = WormScarf.apply(living, amount);
        amount = BrainOfConfusion.apply(living, random, damageSource, amount);
        amount = BreathingReed.apply(living, damageSource, amount);

        amount *= ModUtils.nextFloat(random, 0.8F, 1.2F);  // TODO: 运气
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
            if (living instanceof Boss boss) {
                if (boss.shouldShowMessage()){
                    LivingEntity e = (LivingEntity) boss;
                    Level level = e.level();
                    if (!level.isClientSide) level.players().
                            forEach(player ->
                                    player.sendSystemMessage(Component.translatable("bossevent.confluence.boss_death", e.getDisplayName().getString())
                                            .withStyle(ChatFormatting.DARK_PURPLE)));
                }
            }
        }
    }

    @SubscribeEvent
    public static void livingChangeTarget(LivingChangeTargetEvent event) {
        LivingEntity self = event.getEntity();
        if (!(self instanceof Enemy)) return;
        if (event.getNewTarget() instanceof Player playerO) { // 当新目标为玩家时
            double range = self.getAttributeValue(Attributes.FOLLOW_RANGE);
            double rangeSqr = range * range;
            self.level().players().stream()
                    .filter(player -> player.distanceToSqr(self) < rangeSqr && self.canAttack(player))
                    .max((playerA, playerB) -> {
                        AttributeInstance instanceA = playerA.getAttribute(ModAttributes.getAggro());
                        AttributeInstance instanceB = playerB.getAttribute(ModAttributes.getAggro());
                        if (instanceA != null && instanceB != null) {
                            return (int) (instanceA.getValue() - instanceB.getValue());
                        }
                        return 0;
                    }).ifPresent(player -> {
                        if (player == playerO) return;
                        AttributeInstance instanceO = playerO.getAttribute(ModAttributes.getAggro());
                        AttributeInstance instance = player.getAttribute(ModAttributes.getAggro());
                        if (instanceO != null && instance != null && instanceO.getValue() < instance.getValue()) {
                            event.setNewTarget(player); // 只有当新目标的仇恨值大于旧目标时，才设置新目标
                        }
                    });
        }
    }

    @SubscribeEvent
    public static void livingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        ModAttributes.applyPickupRange(entity);
        for (ObjectIterator<Object2IntMap.Entry<Immunity>> iterator = ((ILivingEntity) entity).confluence$getImmunityTicks().object2IntEntrySet().iterator(); iterator.hasNext(); ) {
            Object2IntMap.Entry<Immunity> entry = iterator.next();
            int remain = entry.getIntValue() - 1;
            if (remain <= 0) {
                iterator.remove();
            } else {
                entry.setValue(remain);
            }
        }
    }

    @SubscribeEvent
    public static void livingHeal(LivingHealEvent event) {
        LivingEntity living = event.getEntity();
        Consumer<Boolean> consumer = event::setCanceled;
        BleedingEffect.apply(living, consumer);
        FrostburnEffect.apply(living, consumer);
        if (event.isCanceled()) {
            return;
        }
        if (living instanceof Player player) {
            player.getCapability(AbilityProvider.CAPABILITY).ifPresent(playerAbility -> {
                if (playerAbility.isVitalCrystalUsed()) {
                    event.setAmount(event.getAmount() * 1.2F);
                }
            });
        }
        if (!(living.level() instanceof ServerLevel level)) return;
        Vec3 pos = living.getEyePosition();
        float amount = Math.round(event.getAmount() * 10) / 10f;
        int intAmount = (int) amount;
        String text = amount % 1 == 0 ? String.valueOf(intAmount) : String.valueOf(amount);
        MutableComponent component = Component.literal(text).withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD);
        level.sendParticles(new DamageIndicatorOptions(component, false), pos.x, pos.y, pos.z, 1, 0.1, 0.1, 0.1, 0);
    }

    @SubscribeEvent
    public static void livingAttack(LivingAttackEvent event) {
        DamageSource damageSource = event.getSource();
        LivingEntity damagingEntity = event.getEntity();
        if (damageSource.getEntity() instanceof LivingEntity livingEntity) {
            if (livingEntity.getItemInHand(event.getEntity().getUsedItemHand()).getItem() instanceof TentacleMaceSword) {
                if (damagingEntity.hasEffect(ModEffects.TENTACLE_SPIKES.get())) {
                    if (damagingEntity.getEffect(ModEffects.TENTACLE_SPIKES.get()).getAmplifier() < 4) {
                        damagingEntity.addEffect(new MobEffectInstance(ModEffects.TENTACLE_SPIKES.get(), 180, damagingEntity.getEffect(ModEffects.TENTACLE_SPIKES.get()).getAmplifier() + 1, false, false, false));
                    } else {
                        damagingEntity.addEffect(new MobEffectInstance(ModEffects.TENTACLE_SPIKES.get(), 180, 4, false, false, false));
                    }
                } else {
                    damagingEntity.addEffect(new MobEffectInstance(ModEffects.TENTACLE_SPIKES.get(), 180, 0, false, false, false));
                }
            }
        }
        if (damageSource.getEntity() instanceof LivingEntity livingEntity) {
            if (livingEntity.getItemInHand(event.getEntity().getUsedItemHand()).getItem() instanceof BloodButchereSword) {
                if (damagingEntity.hasEffect(ModEffects.BLOOD_BUTCHERED.get())) {
                    if (damagingEntity.getEffect(ModEffects.BLOOD_BUTCHERED.get()).getAmplifier() < 4) {
                        damagingEntity.addEffect(new MobEffectInstance(ModEffects.BLOOD_BUTCHERED.get(), 180, damagingEntity.getEffect(ModEffects.BLOOD_BUTCHERED.get()).getAmplifier() + 1, false, false, false));
                    } else {
                        damagingEntity.addEffect(new MobEffectInstance(ModEffects.BLOOD_BUTCHERED.get(), 180, 4, false, false, false));
                    }
                } else {
                    damagingEntity.addEffect(new MobEffectInstance(ModEffects.BLOOD_BUTCHERED.get(), 180, 0, false, false, false));
                }
            }
        }

        if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return;
        }
        damagingEntity.invulnerableTime = 0;
        Entity directEntity = damageSource.getDirectEntity();
        Object2IntMap<Immunity> invTicks = ((ILivingEntity) damagingEntity).confluence$getImmunityTicks();
        Immunity.Types type;
        Immunity cause;
        if (directEntity != null) {
            type = ((Immunity) directEntity).confluence$getImmunityType();
            cause = switch (type) {
                case STATIC -> (Immunity) directEntity.getType();
                case LOCAL -> (Immunity) directEntity;
            };
        } else {
            cause = (Immunity) (Object) damageSource.type();
        }
        if (invTicks.containsKey(cause)) {
            event.setCanceled(true);
            return;
        } else {
            int time = ((ILivingEntity) damagingEntity).c$getInvulnerableTime(cause.confluence$getImmunityDuration(damagingEntity.level().registryAccess()));
            invTicks.put(cause, time);
        }
    }



    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity damagingEntity = event.getEntity();
        DamageSource damageSource = event.getSource();
        Entity causer = damageSource.getEntity();
        // 这个return只针对暴击判定和数值显示，如果以后还有其他用处还要拆出来
        if (event.isCanceled() || damageSource.is(DamageTypes.GENERIC_KILL) || !(event.getEntity().level() instanceof ServerLevel level)) return;
        float amount = event.getAmount();
        boolean crit = false;
        if (!ModAttributes.hasCustomAttribute(ModAttributes.CRIT_CHANCE.get()) && causer instanceof Player player) {
            double chance = player.getAttributeValue(ModAttributes.CRIT_CHANCE.get());
            if (damagingEntity.level().random.nextFloat() < chance) {
                amount *= 2;
                event.setAmount(amount);
                player.crit(damagingEntity);
                crit = true;
            }
            if (damageSource.getDirectEntity() instanceof AbstractArrow arrow) {
                crit |= arrow.isCritArrow();
            }
        }
        float roundedAmount = Math.round(amount * 10) / 10f;
        int intAmount = (int) roundedAmount;
        String text = roundedAmount % 1 == 0 ? String.valueOf(intAmount) : String.valueOf(roundedAmount);
        Vec3 pos = damagingEntity.getEyePosition();
        MutableComponent component = Component.literal(text).withStyle(crit ? ChatFormatting.DARK_RED : ChatFormatting.GOLD, ChatFormatting.BOLD);
        level.sendParticles(new DamageIndicatorOptions(component, crit), pos.x, pos.y, pos.z, 1, 0.1, 0.1, 0.1, 0);
    }

    @SubscribeEvent
    public static void mobFinalizeSpawn(MobSpawnEvent.FinalizeSpawn event) {
        Mob mob = event.getEntity();
        RandomSource randomSource = mob.getRandom();
        if (mob instanceof DemonEye demonEye) {
            demonEye.setVariant(DemonEyeVariant.random(randomSource));
        } else if (mob instanceof BlackSlime blackSlime) {
            blackSlime.finalizeSpawn(randomSource, event.getDifficulty());
        } else if (mob instanceof Boss boss) {
            if (boss.shouldShowMessage()){
                LivingEntity e = (LivingEntity) boss;
                Level level = e.level();
                if (!level.isClientSide) level.players().
                        forEach(player ->
                                player.sendSystemMessage(Component.translatable(
                                        "bossevent.confluence.boss_generate",
                                                e.getDisplayName().getString())
                                        .withStyle(ChatFormatting.DARK_PURPLE)));
            }
        }
    }

    @SubscribeEvent
    public static void entityJoinLevel(EntityJoinLevelEvent event) {
        Level level = event.getLevel();
        if (event.loadedFromDisk() || !(level instanceof ServerLevel serverLevel)) return;
        if (event.getEntity() instanceof Zombie zombie && !zombie.isBaby() && !zombie.isVehicle() && zombie.getRandom().nextFloat() < 0.05F) {
            BaseSlime slime = ModEntities.BLUE_SLIME.get().create(level);
            if (slime != null) {
                slime.moveTo(zombie.getX(), zombie.getY(), zombie.getZ(), zombie.getYRot(), 0.0F);
                slime.finalizeSpawn(serverLevel, level.getCurrentDifficultyAt(zombie.blockPosition()), MobSpawnType.JOCKEY, null, null);
                slime.startRiding(zombie);
                level.addFreshEntity(slime);
            }
        }

        // 进入地狱刷新饰品能力，延迟刷新
        if (event.getEntity() instanceof ServerPlayer player) {
            DelayedTaskExecutor.getInstance("FlushPlayerAbility", serverLevel.getServer())
                    .registerTask(new DelayedTaskExecutor.DelayedTask(() -> {
                        CuriosUtils.getCurios(player).forEach(item -> {
                            NetworkHandler.CHANNEL.send(
                                    PacketDistributor.PLAYER.with(() -> player),
                                    new FlushPlayerAbilityPacketS2C());
                        });
                    }, 50));
        }

        if (event.getEntity() instanceof AbstractArrow arrow && arrow.getOwner() instanceof LivingEntity living) {
            ModAttributes.applyToArrow(living, arrow);
            MoltenQuiver.applyToArrow(living, arrow);
        }
    }

    @SubscribeEvent
    public static void livingBreathe(LivingBreatheEvent event) {
        if (event.canBreathe()) return;
        LivingEntity living = event.getEntity();
        if (living.hasEffect(ModEffects.SHIMMER.get())) {
            event.setCanBreathe(true);
        } else if (BreathingReed.hasReed(living)) {
            event.setConsumeAirAmount(BreathingReed.getDecrease(living.getRandom()));
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

    @SubscribeEvent
    public static void entityMount(EntityMountEvent event) {
        if (event.isMounting() || event.getLevel().isClientSide) return;
        if (event.getEntityMounting() instanceof Player player && event.getEntityBeingMounted() instanceof AbstractMinecart abstractMinecart) {
            Item item = Confluence.MINECART_CURIO.get(abstractMinecart.getType());
            if (item == null) return;
            ItemStack itemStack = new ItemStack(item);
            if (CuriosUtils.getSlot(player, "minecart", 0).isEmpty()) {
                CuriosApi.getCuriosInventory(player).ifPresent(inv -> inv.setEquippedCurio("minecart", 0, itemStack));
            } else {
                player.addItem(itemStack);
            }
            ((EntityAccessor) player).setVehicle(null);
            ((EntityAccessor) abstractMinecart).callRemovePassenger(player);
            abstractMinecart.discard();
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void curios(CurioEquipEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            ModTriggers.CURIOS_EQUIPPED.trigger(serverPlayer, event.getStack());
        }
    }

    @SubscribeEvent
    public static void explosionDetonate(ExplosionEvent.Detonate event) {
        if (event.getExplosion().getExploder() instanceof ScarabBombEntity) {
            event.getAffectedEntities().removeIf(entity -> entity instanceof ItemEntity);
        }
    }

    @SubscribeEvent
    public static void onRegisterTeamData(RegisterTeamDataEvent event) {
        ModEntities.ENTITIES.getEntries().forEach(entry -> {
            EntityType<?> entityType = entry.get();
            event.registerTeamData(entityType, data -> data.setCanTeam(false));
        });
    }
}
