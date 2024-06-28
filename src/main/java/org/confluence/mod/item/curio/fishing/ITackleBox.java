package org.confluence.mod.item.curio.fishing;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.util.CuriosUtils;
import org.confluence.mod.util.IFishingHook;

public interface ITackleBox {
    static void apply(FishingHook hook, Player player) {
        IFishingHook fishingHook = (IFishingHook) hook;
        ItemStack bait = fishingHook.confluence$getBait();
        if (bait == null) return;
        float factor = CuriosUtils.noSameCurio(player, ITackleBox.class) ? 1.0F : 2.0F;
        if (player.getRandom().nextFloat() < 1.0F / (factor + fishingHook.confluence$getBonus() / 6.0F)) {
            bait.shrink(1);
        }
    }
}
