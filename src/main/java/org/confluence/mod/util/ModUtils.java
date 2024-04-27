package org.confluence.mod.util;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class ModUtils {
    public static float nextFloat(RandomSource randomSource, float origin, float bound) {
        if (origin >= bound) {
            throw new IllegalArgumentException("bound - origin is non positive");
        } else {
            return origin + randomSource.nextFloat() * (bound - origin);
        }
    }

    public static void createItemEntity(Item item, int count, double x, double y, double z, Level level) {
        if (count <= 0) return;
        ItemEntity itemEntity = new ItemEntity(level, x, y, z, new ItemStack(item, count));
        itemEntity.setPickUpDelay(40);
        level.addFreshEntity(itemEntity);
    }
}
