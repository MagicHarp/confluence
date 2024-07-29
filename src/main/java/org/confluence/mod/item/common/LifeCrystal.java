package org.confluence.mod.item.common;

import net.minecraft.sounds.SoundSource;
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
import org.confluence.mod.misc.ModSounds;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class LifeCrystal extends Item {
    public static final UUID HEALTH_UUID = UUID.fromString("B81C17EE-E57B-1A23-B313-599A5C27245A");

    public LifeCrystal() {
        super(new Properties().rarity(ModRarity.GREEN));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        player.getCapability(AbilityProvider.CAPABILITY).ifPresent(playerAbility -> {
            if (playerAbility.increaseCrystals() && !level.isClientSide) {
                itemStack.shrink(1);
                applyModifier(player, playerAbility);
                player.heal(4.0F);
                level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.LIFE_CRYSTAL_USE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        });
        return InteractionResultHolder.success(itemStack);
    }

    public static void applyModifier(Player player, PlayerAbility playerAbility) {
        AttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MAX_HEALTH);
        if (attributeInstance == null) return;
        attributeInstance.removeModifier(HEALTH_UUID);
        attributeInstance.addPermanentModifier(new AttributeModifier(
            HEALTH_UUID, "Life Crystal",
            playerAbility.getCrystals() * 4.0,
            AttributeModifier.Operation.ADDITION
        ));
    }
}
