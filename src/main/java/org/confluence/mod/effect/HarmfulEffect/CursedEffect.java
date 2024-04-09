package org.confluence.mod.effect.HarmfulEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

public class CursedEffect extends MobEffect {   //诅咒 禁止玩家使用物品
    public CursedEffect() {
        super(MobEffectCategory.HARMFUL, 0x4F4F4F);
    }

    public static void onAdd(LivingEntity entity, LivingEntityUseItemEvent.Stop event) {
        if (entity instanceof Player && !entity.isSpectator() && entity.isUsingItem()) {
            event.setCanceled(true);
        }
    }
}
