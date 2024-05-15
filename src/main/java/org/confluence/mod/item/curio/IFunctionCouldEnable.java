package org.confluence.mod.item.curio;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface IFunctionCouldEnable {
    default String getEnableKey() {
        return "enable";
    }

    default boolean isEnabled(ItemStack itemStack) {
        return itemStack.getTag() == null || itemStack.getTag().getBoolean(getEnableKey());
    }

    default void cycleEnable(ItemStack itemStack) {
        CompoundTag tag = itemStack.getOrCreateTag();
        String key = getEnableKey();
        tag.putBoolean(key, !tag.getBoolean(key));
    }
}
