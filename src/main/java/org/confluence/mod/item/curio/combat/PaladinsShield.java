package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.scores.Team;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.confluence.mod.datagen.limit.CustomName;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class PaladinsShield extends BaseCurioItem implements CustomName {
    public static final UUID ARMOR_UUID = UUID.fromString("276CFD23-08F7-50D5-8797-C7F4E1DAD96E");
    public static final UUID RESISTANCE_UUID = UUID.fromString("E4816CB8-0453-3050-70A6-2D0075E84FC5");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE;

    public PaladinsShield(Rarity rarity) {
        super(rarity);
    }

    public PaladinsShield() {
        super(ModRarity.YELLOW);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTE == null) {
            ATTRIBUTE = ImmutableMultimap.of(
                Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "Paladins Shield", ModConfigs.PALADINS_SHIELD_ARMOR.get(), AttributeModifier.Operation.ADDITION),
                Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(RESISTANCE_UUID, "Paladins Shield", ModConfigs.PALADINS_SHIELD_RESISTANCE.get(), AttributeModifier.Operation.ADDITION)
            );
        }
        return ATTRIBUTE;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer serverPlayer && serverPlayer.level().getGameTime() % 200 == 0) {
            Team team = serverPlayer.getTeam();
            for (Player player : serverPlayer.level().players()) {
                if (player.getTeam() != team) continue;
                player.addEffect(new MobEffectInstance(ModEffects.PALADINS_SHIELD.get(), 600, player == serverPlayer ? 1 : 0));
            }
        }
    }

    public static float apply(LivingEntity living, DamageSource damageSource, float amount) {
        if (living instanceof ServerPlayer serverPlayer && !isOwner(serverPlayer)) {
            MutableFloat atomic = new MutableFloat(amount);
            Team team = serverPlayer.getTeam();
            serverPlayer.level().players().stream()
                .filter(player -> player != serverPlayer && // player不是自己
                    player != damageSource.getEntity() && // player不是给自己造成过伤害的
                    player.getTeam() == team && // player的队伍与自己的相同
                    player.getHealth() / player.getMaxHealth() > 0.25F && // player血量大于最大血量的25%
                    isOwner(player) && // player拥有圣骑士盾
                    player.distanceToSqr(serverPlayer) < 1024.0 // player与自己的距离在32米内
                )
                .min((playerA, playerB) -> (int) (playerA.distanceToSqr(serverPlayer) - playerB.distanceToSqr(serverPlayer))).ifPresent(player -> {
                    float damage = amount * 0.25F;
                    player.hurt(living.damageSources().playerAttack(serverPlayer), damage);
                    atomic.subtract(damage);
                });
            return atomic.getValue();
        }
        return amount;
    }

    public static boolean isOwner(LivingEntity living) {
        MobEffectInstance effect = living.getEffect(ModEffects.PALADINS_SHIELD.get());
        return effect != null && effect.getAmplifier() != 0;
    }

    @Override
    public String getGenName() {
        return "Paladin's Shield";
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.paladins_shield.info"),
            Component.translatable("item.confluence.paladins_shield.info2"),
            Component.translatable("item.confluence.paladins_shield.info3"),
            Component.translatable("item.confluence.paladins_shield.info4")
        };
    }
}
