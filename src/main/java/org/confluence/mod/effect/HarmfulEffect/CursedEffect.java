package org.confluence.mod.effect.HarmfulEffect;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class CursedEffect extends MobEffect {   //诅咒 禁止玩家使用物品
    public CursedEffect() {
        super(MobEffectCategory.HARMFUL, 0x4F4F4F);
    }

    public static void noUseItem(PlayerInteractEvent event) {
        if (event.getEntity().isUsingItem()) {
            event.setCanceled(true);
        }
    }

    public void onAdd(Player player) {
        if (player.hasEffect(this)) {
            noUseItem(new PlayerInteractEvent.RightClickItem(player, InteractionHand.MAIN_HAND));
        }
    }
}
