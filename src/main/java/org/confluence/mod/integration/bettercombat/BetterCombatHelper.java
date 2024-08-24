package org.confluence.mod.integration.bettercombat;

import net.bettercombat.logic.WeaponRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

public class BetterCombatHelper {
    private static Boolean isLoaded;

    public static boolean isLoaded() {
        if (isLoaded == null) {
            isLoaded = ModList.get().isLoaded("bettercombat");
        }
        return isLoaded;
    }

    public static boolean hasWeaponAttributes(ItemStack itemStack) {
        return WeaponRegistry.getAttributes(itemStack) != null;
    }
}
