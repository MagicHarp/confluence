package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.scores.Team;
import org.confluence.mod.datagen.limit.CustomName;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.util.CuriosUtils;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class PaladinsShield extends BaseCurioItem implements CustomName {
    public static final UUID ARMOR_UUID = UUID.fromString("276CFD23-08F7-50D5-8797-C7F4E1DAD96E");
    public static final UUID RESISTANCE_UUID = UUID.fromString("E4816CB8-0453-3050-70A6-2D0075E84FC5");
    private static final ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE = ImmutableMultimap.of(
        Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "Paladins Shield", 6, AttributeModifier.Operation.ADDITION),
        Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(RESISTANCE_UUID, "Paladins Shield", 1.0, AttributeModifier.Operation.ADDITION)
    );

    public PaladinsShield(Rarity rarity) {
        super(rarity);
    }

    public PaladinsShield() {
        super(ModRarity.YELLOW);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return ATTRIBUTE;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer serverPlayer && serverPlayer.level().getGameTime() % 200 == 0) {
            Team team = serverPlayer.getTeam();
            for (Player player : serverPlayer.level().players()) {
                if (shouldSkip(player, team, serverPlayer)) continue;
                player.addEffect(new MobEffectInstance(ModEffects.PALADINS_SHIELD.get(), 200));
            }
        }
    }

    public static float apply(LivingEntity living, float amount) {
        if (living instanceof ServerPlayer serverPlayer && serverPlayer.hasEffect(ModEffects.PALADINS_SHIELD.get()) && !CuriosUtils.hasCurio(serverPlayer, PaladinsShield.class)) {
            AtomicDouble atomic = new AtomicDouble(amount);
            Team team = serverPlayer.getTeam();
            serverPlayer.level().players().stream()
                .filter(player -> !shouldSkip(player, team, serverPlayer) && player.getHealth() / player.getMaxHealth() > 0.25F && CuriosUtils.hasCurio(player, PaladinsShield.class))
                .min((playerA, playerB) -> (int) (playerA.distanceTo(serverPlayer) - playerB.distanceTo(serverPlayer))).ifPresent(player -> {
                    float damage = amount * 0.25F;
                    player.hurt(living.damageSources().playerAttack(serverPlayer), damage);
                    atomic.set(amount - damage);
                });
            return atomic.floatValue();
        }
        return amount;
    }

    protected static boolean shouldSkip(Player playerA, Team team, Player playerB) {
        return playerA.getTeam() != team || playerA.getScoreboardName().equals(playerB.getScoreboardName());
    }

    @Override
    public String getGenName() {
        return "Paladin's Shield";
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.paladins_shield.info"),
            Component.translatable("item.confluence.paladins_shield.info2"),
            Component.translatable("item.confluence.paladins_shield.info3")
        };
    }
}
