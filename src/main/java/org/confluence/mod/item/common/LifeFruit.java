package org.confluence.mod.item.common;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.capability.ability.PlayerAbility;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class LifeFruit extends Item {
    public static final UUID HEALTH_UUID = UUID.fromString("CFF42408-3856-7220-501E-5657A28DFDE5");

    public LifeFruit() {
        super(new Properties().rarity(ModRarity.LIME));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            player.getCapability(AbilityProvider.CAPABILITY).ifPresent(playerAbility -> {
                if (playerAbility.increaseFruits()) {
                    itemStack.shrink(1);
                    applyModifier(player, playerAbility);
                    // todo sound
                }
            });
        }
        return InteractionResultHolder.success(itemStack);
    }

    public static void applyModifier(Player player, PlayerAbility playerAbility) {
        AttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MAX_HEALTH);
        if (attributeInstance == null) return;
        attributeInstance.removeModifier(HEALTH_UUID);
        attributeInstance.addPermanentModifier(new AttributeModifier(
            HEALTH_UUID, "Life Fruit",
            playerAbility.getFruits(),
            AttributeModifier.Operation.ADDITION
        ));
    }
}
