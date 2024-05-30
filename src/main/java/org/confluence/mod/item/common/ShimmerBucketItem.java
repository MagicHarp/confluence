package org.confluence.mod.item.common;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.fluid.ModFluids;
import org.confluence.mod.misc.ModRarity;

public class ShimmerBucketItem extends BucketItem {
    public ShimmerBucketItem() {
        super(ModFluids.SHIMMER.fluid(), new Item.Properties().rarity(ModRarity.RED).stacksTo(1));
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
