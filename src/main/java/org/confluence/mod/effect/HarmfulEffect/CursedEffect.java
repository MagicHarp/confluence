package org.confluence.mod.effect.HarmfulEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class CursedEffect extends MobEffect {   //诅咒 禁止玩家使用物品
    public CursedEffect() {
        super(MobEffectCategory.HARMFUL, 0x4F4F4F);
    }

    public static void onRightClick(LivingEntity entity, PlayerInteractEvent.RightClickItem event) {
        if (!entity.isSpectator() && !entity.getUseItem().isEmpty() && entity.hasEffect(new CursedEffect())) {
            event.setCanceled(true);
        }
    }

    public static void onLeftClick(InputEvent.InteractionKeyMappingTriggered event) {
        if (event.isUseItem() || event.isAttack() || event.isPickBlock()) {
            event.setCanceled(true);
        }
    }
}
