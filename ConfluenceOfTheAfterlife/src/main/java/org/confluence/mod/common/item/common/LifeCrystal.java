package org.confluence.mod.common.item.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
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
import org.confluence.mod.common.init.ModSoundEvents;
import org.confluence.mod.common.item.CustomRarityItem;
import org.jetbrains.annotations.NotNull;

public class LifeCrystal extends CustomRarityItem {
    public static final ResourceLocation ID = Confluence.asResource("life_crystal");

    public LifeCrystal() {
        super(new Properties().component(ModDataComponentTypes.MOD_RARITY, ModRarity.GREEN));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            GamePlay gamePlay = player.getData(ModAttachments.GAMEPLAY.get());
            if (gamePlay.increaseCrystals()) {
                itemStack.shrink(1);
                applyModifier(player, gamePlay);
                player.heal(4.0F);
                level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.LIFE_CRYSTAL_USE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }
        return InteractionResultHolder.success(itemStack);
    }

    public static void applyModifier(Player player, GamePlay gamePlay) {
        AttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MAX_HEALTH);
        if (attributeInstance == null) return;
        attributeInstance.removeModifier(ID);
        attributeInstance.addPermanentModifier(new AttributeModifier(ID, gamePlay.getCrystals() * 4.0, AttributeModifier.Operation.ADD_VALUE));
    }
}
