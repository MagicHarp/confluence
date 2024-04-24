package org.confluence.mod.item.food;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import static net.minecraft.world.item.Items.GLASS_BOTTLE;

public class BottleFoodItem extends Item {
    public BottleFoodItem(FoodProperties foodProperties) {
        super(new Properties().food(foodProperties).craftRemainder(GLASS_BOTTLE).stacksTo(64));
    }
}
