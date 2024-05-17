package org.confluence.mod.util;

import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.fishing.IBait;

public interface IFishingHook {
    void c$setIsLavaHook();

    ItemStack c$getBait();

    default float c$getBonus() {
        return ((IBait) c$getBait().getItem()).getBaitBonus();
    }
}
