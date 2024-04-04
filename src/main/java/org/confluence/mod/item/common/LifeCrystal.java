package org.confluence.mod.item.common;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.capability.ability.PlayerAbilityProvider;
import org.confluence.mod.item.ModRarity;
import org.jetbrains.annotations.NotNull;

public class LifeCrystal extends Item {
    public LifeCrystal() {
        super(new Properties().rarity(ModRarity.GREEN));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            AttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MAX_HEALTH);
            if (attributeInstance != null) {
                player.getCapability(PlayerAbilityProvider.CAPABILITY).ifPresent(playerAbility -> {
                    if (playerAbility.increaseCrystals()) {
                        attributeInstance.setBaseValue(playerAbility.getCrystals() * 4);
                        itemStack.shrink(1);
                        // todo sound
                    }
                });
            }
        }
        return InteractionResultHolder.success(itemStack);
    }
}
