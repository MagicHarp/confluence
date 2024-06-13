package org.confluence.mod.integration.bettercombat;

import net.bettercombat.logic.WeaponRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

public interface BetterCombatHelper {
    static boolean isLoaded() {
        return ModList.get().isLoaded("bettercombat");
    }

    static boolean hasWeaponAttributes(ItemStack itemStack) {
        return WeaponRegistry.getAttributes(itemStack) != null;
    }
}
