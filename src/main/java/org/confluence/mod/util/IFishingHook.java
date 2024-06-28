package org.confluence.mod.util;

import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.fishing.Baits;
import org.jetbrains.annotations.Nullable;

public interface IFishingHook {
    void confluence$setIsLavaHook();

    boolean confluence$isLavaHook();

    @Nullable ItemStack confluence$getBait();

    default float confluence$getBonus() {
        ItemStack itemStack = confluence$getBait();
        if (itemStack != null && itemStack.getItem() instanceof Baits.IBait iBait) {
            return iBait.getBaitBonus();
        }
        return 0.0F;
    }
}
