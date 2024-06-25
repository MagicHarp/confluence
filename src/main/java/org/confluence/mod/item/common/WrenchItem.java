package org.confluence.mod.item.common;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class WrenchItem extends Item {
    public final int color;

    public WrenchItem(int color) {
        super(new Properties().stacksTo(1));
        this.color = color;
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return pStack.getTag() != null && pStack.getTag().contains("blockPos");
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }

    public static void writeBlockPos(ItemStack itemStack, BlockPos pos) {
        itemStack.getOrCreateTag().put("blockPos", NbtUtils.writeBlockPos(pos));
    }

    public static @Nullable BlockPos readBlockPos(ItemStack itemStack) {
        if (itemStack.getTag() == null) return null;
        return itemStack.getTag().contains("blockPos") ? NbtUtils.readBlockPos(itemStack.getTag().getCompound("blockPos")) : null;
    }

    public static void removeBlockPos(ItemStack itemStack) {
        itemStack.getOrCreateTag().remove("blockPos");
    }
}
