package org.confluence.mod.item.food;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class FireproofFoodItem extends Item {
    public FireproofFoodItem(FoodProperties foodProperties) {
        super(new Properties().food(foodProperties).stacksTo(64).fireResistant());

    }
}
