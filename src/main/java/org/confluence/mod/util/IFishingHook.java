package org.confluence.mod.util;

import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.fishing.IBait;

public interface IFishingHook {
    void c$setIsLavaHook();

    ItemStack c$getBait();

    default float c$getBonus() {
        ItemStack itemStack = c$getBait();
        if (itemStack != null && itemStack.getItem() instanceof IBait iBait) {
            return iBait.getBaitBonus();
        }
        return 0.0F;
    }
}
