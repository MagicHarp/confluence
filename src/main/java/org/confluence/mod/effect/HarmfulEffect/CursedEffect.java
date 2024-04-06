package org.confluence.mod.effect.HarmfulEffect;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.confluence.mod.event.EffectEvents;

public class CursedEffect extends MobEffect {   //诅咒 禁止玩家使用物品
    EffectEvents effectEvents = new EffectEvents();
    public CursedEffect() {
        super(MobEffectCategory.HARMFUL, 0x4F4F4F);
    }

    public void onAdd(Player player) {
        if (player.hasEffect(this)) {
            effectEvents.noUseItem(new PlayerInteractEvent.RightClickItem(player, InteractionHand.MAIN_HAND));
        }
    }
}
