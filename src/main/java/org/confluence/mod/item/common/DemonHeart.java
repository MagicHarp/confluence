package org.confluence.mod.item.common;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.List;

public class DemonHeart extends Item implements ModRarity.Expert {
    public DemonHeart() {
        super(new Properties().stacksTo(1).rarity(ModRarity.EXPERT).fireResistant());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        CuriosApi.getCuriosInventory(pPlayer).ifPresent(iCuriosItemHandler -> {
            ICurioStacksHandler iCurioStacksHandler = iCuriosItemHandler.getCurios().get("accessory");
            if (iCurioStacksHandler != null) {
                if (iCurioStacksHandler.getSlots() < 7) {
                    itemStack.shrink(1);
                    iCurioStacksHandler.grow(1);
                }
            }
        });
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.confluence.demon_heart.tooltip"));
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack pStack) {
        return withColor(getDescriptionId());
    }
}
