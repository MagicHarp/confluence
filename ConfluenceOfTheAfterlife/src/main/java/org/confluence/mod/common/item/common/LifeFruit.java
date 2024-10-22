package org.confluence.mod.common.item.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.attachment.GamePlay;
import org.confluence.mod.common.component.ModRarity;
import org.confluence.mod.common.init.ModAttachments;
import org.confluence.mod.common.init.ModDataComponentTypes;
import org.confluence.mod.common.item.CustomRarityItem;
import org.jetbrains.annotations.NotNull;

public class LifeFruit extends CustomRarityItem {
    public static final ResourceLocation ID = Confluence.asResource("life_fruit");

    public LifeFruit() {
        super(new Properties().component(ModDataComponentTypes.MOD_RARITY, ModRarity.LIME));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            GamePlay gamePlay = player.getData(ModAttachments.GAMEPLAY.get());
            if (gamePlay.increaseFruits() && !level.isClientSide) {
                itemStack.shrink(1);
                applyModifier(player, gamePlay);
                player.heal(1.0F);
                // todo sound
            }
        }
        return InteractionResultHolder.success(itemStack);
    }

    public static void applyModifier(Player player, GamePlay gamePlay) {
        AttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MAX_HEALTH);
        if (attributeInstance == null) return;
        attributeInstance.removeModifier(ID);
        attributeInstance.addPermanentModifier(new AttributeModifier(ID, gamePlay.getFruits(), AttributeModifier.Operation.ADD_VALUE));
    }
}
