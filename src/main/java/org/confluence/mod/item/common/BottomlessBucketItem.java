package org.confluence.mod.item.common;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

public class BottomlessBucketItem extends BucketItem {
    public BottomlessBucketItem(Supplier<? extends FlowingFluid> supplier, Rarity rarity) {
        super(supplier, new Item.Properties().rarity(rarity).stacksTo(1));
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return itemStack;
    }
}
