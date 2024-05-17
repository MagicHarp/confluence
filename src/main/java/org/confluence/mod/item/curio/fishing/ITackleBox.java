package org.confluence.mod.item.curio.fishing;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import org.confluence.mod.util.CuriosUtils;
import org.confluence.mod.util.IFishingHook;

public interface ITackleBox {
    static void apply(FishingHook hook, Player player){
        IFishingHook fishingHook = (IFishingHook) hook;
        float factor = CuriosUtils.noSameCurio(player, ITackleBox.class) ? 1.0F : 2.0F;
        if (player.getRandom().nextFloat() < 1.0F / (factor + fishingHook.c$getBonus() / 6.0F)) {
            fishingHook.c$getBait().shrink(1);
        }
    }
}
