package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.scores.Team;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.ModRarity;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class FrozenShield extends PaladinsShield {
    public static final UUID ARMOR_UUID = UUID.fromString("9F4C8995-F29F-7A5F-77E2-B1F05011E962");
    public static final UUID RESISTANCE_UUID = UUID.fromString("C5CFE194-31B4-B5DC-35DA-F1F668ECB89E");
    private static final ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE = ImmutableMultimap.of(
        Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "Frozen Shield", 6, AttributeModifier.Operation.ADDITION),
        Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(RESISTANCE_UUID, "Frozen Shield", 1.0, AttributeModifier.Operation.ADDITION)
    );

    public FrozenShield() {
        super(ModRarity.PINK);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return ATTRIBUTE;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer serverPlayer && serverPlayer.level().getGameTime() % 200 == 0) {
            Team team = serverPlayer.getTeam();
            float health = serverPlayer.getHealth() / serverPlayer.getMaxHealth();
            for (Player player : serverPlayer.level().players()) {
                if (shouldSkip(player, team, serverPlayer)) continue;
                player.addEffect(new MobEffectInstance(ModEffects.PALADINS_SHIELD.get(), 200));
                if (health > 0.25F && health < 0.5F) {
                    //player.addEffect()
                }
            }
        }
    }
}
