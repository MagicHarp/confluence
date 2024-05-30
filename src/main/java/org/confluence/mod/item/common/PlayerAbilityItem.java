package org.confluence.mod.item.common;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.capability.ability.PlayerAbility;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public class PlayerAbilityItem extends Item {
    private final Function<PlayerAbility, Boolean> toCheck;
    private final Consumer<PlayerAbility> toEnable;

    public PlayerAbilityItem(Function<PlayerAbility, Boolean> toCheck, Consumer<PlayerAbility> toEnable) {
        super(new Properties().rarity(ModRarity.LIGHT_PURPLE).fireResistant());
        this.toCheck = toCheck;
        this.toEnable = toEnable;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return UseAnim.SPYGLASS;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 15;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity) {
        if (pLivingEntity instanceof Player player) {
            player.getCapability(AbilityProvider.CAPABILITY).ifPresent(playerAbility -> {
                if (!toCheck.apply(playerAbility)) {
                    toEnable.accept(playerAbility);
                    if (!pLevel.isClientSide) {
                        pStack.shrink(1);
                        modifier(player, playerAbility);
                    }
                    // todo sound
                }
            });
        }
        return pStack;
    }

    protected void modifier(Player player, PlayerAbility playerAbility) {}

    public static class AegisApple extends PlayerAbilityItem {
        public static final UUID ARMOR_UUID = UUID.fromString("B85999E6-47C2-9EB5-573F-7D43C9078D7F");

        public AegisApple() {
            super(PlayerAbility::isAegisAppleUsed, PlayerAbility::setAegisAppleUsed);
        }

        @Override
        protected void modifier(Player player, PlayerAbility playerAbility) {
            applyModifier(player, playerAbility);
        }

        public static void applyModifier(Player player, PlayerAbility playerAbility) {
            if (!playerAbility.isAegisAppleUsed()) return;
            AttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.ARMOR);
            if (attributeInstance == null) return;
            attributeInstance.removeModifier(ARMOR_UUID);
            attributeInstance.addPermanentModifier(
                new AttributeModifier(ARMOR_UUID, "Aegis Apple", 4, AttributeModifier.Operation.ADDITION)
            );
        }
    }
}
