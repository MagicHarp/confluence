package org.confluence.mod.event;

import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.Confluence;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.item.curio.combat.*;
import org.confluence.mod.item.curio.expert.BrainOfConfusion;
import org.confluence.mod.item.curio.expert.WormScarf;
import org.confluence.mod.item.curio.informational.IDPSMeter;
import org.confluence.mod.item.curio.movement.IFallResistance;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.EntityKilledPacketS2C;
import org.confluence.mod.util.ModUtils;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ForgeEvents {
    @SubscribeEvent
    public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (player.getCapability(AbilityProvider.CAPABILITY).isPresent()) return;
            event.addCapability(new ResourceLocation(Confluence.MODID, "ability"), new AbilityProvider());
        }
    }

    @SubscribeEvent
    public static void livingHurt(LivingHurtEvent event) {
        LivingEntity living = event.getEntity();
        if (living.level().isClientSide) return;
        DamageSource damageSource = event.getSource();
        if (damageSource.is(DamageTypes.FELL_OUT_OF_WORLD) || damageSource.is(DamageTypes.GENERIC_KILL)) return;
        RandomSource random = living.level().random;
        float amount = event.getAmount();

        IHoneycomb.apply(living, random);
        IStarCloak.apply(living, random);
        PanicNecklace.apply(living);

        amount = ModAttributes.applyMagicDamage(damageSource, amount);
        amount = ModAttributes.applyRangedDamage(living, damageSource, amount);
        amount = PaladinsShield.apply(living, damageSource, amount);
        amount = FrozenTurtleShell.apply(living, amount);
        amount = ILavaHurtReduce.apply(living, damageSource, amount);
        amount = IFallResistance.apply(living, damageSource, amount);
        amount = WormScarf.apply(living, amount);
        amount = BrainOfConfusion.apply(living, random, amount);

        if (ModConfigs.RANDOM_ATTACK_DAMAGE.get()) {
            amount *= ModUtils.nextFloat(random,
                    ModConfigs.RANDOM_ATTACK_DAMAGE_MIN.get().floatValue(),
                    ModConfigs.RANDOM_ATTACK_DAMAGE_MAX.get().floatValue()
            );
        }
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
        ModAttributes.applyPickupRange(event.getEntity());
    }

    @SubscribeEvent
    public static void entityJoinLevel(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide && event.getEntity() instanceof AbstractArrow arrow && arrow.getOwner() instanceof LivingEntity living) {
            ModAttributes.applyToArrow(living, arrow);
            MoltenQuiver.applyToArrow(living, arrow);
        }
    }
}
