package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.scores.Team;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.effect.OwnerMobEffectInstance;
import org.confluence.mod.item.curio.BaseCurioItem;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class PaladinsShield extends BaseCurioItem {
    public static final UUID ARMOR_UUID = UUID.fromString("276CFD23-08F7-50D5-8797-C7F4E1DAD96E");
    public static final UUID RESISTANCE_UUID = UUID.fromString("E4816CB8-0453-3050-70A6-2D0075E84FC5");
    private static final ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE = ImmutableMultimap.of(
        Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "Paladins Shield", 6, AttributeModifier.Operation.ADDITION),
        Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(RESISTANCE_UUID, "Paladins Shield", 1.0, AttributeModifier.Operation.ADDITION)
    );

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return ATTRIBUTE;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer serverPlayer && serverPlayer.level().getGameTime() % 200 == 0) {
            Team team = serverPlayer.getTeam();
            for (Player player : serverPlayer.level().players()) {
                if (player.getTeam() != team) continue;
                player.addEffect(new OwnerMobEffectInstance(new MobEffectInstance(ModEffects.PALADINS_SHIELD.get(), 200), serverPlayer));
            }
        }
    }

    public static float apply(LivingEntity living, float amount) {
        if (living instanceof ServerPlayer serverPlayer && serverPlayer.hasEffect(ModEffects.PALADINS_SHIELD.get())) {
            MobEffectInstance instance = serverPlayer.getEffect(ModEffects.PALADINS_SHIELD.get());
            if (instance instanceof OwnerMobEffectInstance ownerMobEffectInstance) {
                Player player = ownerMobEffectInstance.getOwner();
                if (player.getHealth() / player.getMaxHealth() > 0.25F) {
                    float damage = amount * 0.25F;
                    player.hurt(living.damageSources().playerAttack(serverPlayer), damage);
                    return amount - damage;
                }
            }
        }
        return amount;
    }
}
