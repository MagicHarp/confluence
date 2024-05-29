package org.confluence.mod.item.food;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.FireChargeItem;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public class BaseFoodItem extends Item {
    public BaseFoodItem(FoodProperties foodProperties) {
        super(new Item.Properties().food(foodProperties).stacksTo(64));

    }
}
