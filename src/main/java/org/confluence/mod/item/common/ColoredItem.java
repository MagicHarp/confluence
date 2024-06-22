package org.confluence.mod.item.common;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.confluence.mod.datagen.limit.CustomModel;

public class ColoredItem extends Item implements CustomModel {
    public ColoredItem(Properties pProperties) {
        super(pProperties);
    }

    public ColoredItem(Rarity rarity) {
        this(new Properties().rarity(rarity));
    }

    public static void setColor(ItemStack itemStack, int rgb) {
        itemStack.getOrCreateTag().putInt("color", rgb);
    }

    public static int getColor(ItemStack itemStack) {
        return itemStack.getTag() == null ? 0x66CCFF : itemStack.getTag().getInt("color");
    }
}
