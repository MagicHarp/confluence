package org.confluence.mod.item.food;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class BaseFoodItem extends Item {
    public BaseFoodItem(FoodProperties foodProperties) {
        super(new Item.Properties().food(foodProperties).stacksTo(64));

    }
}
