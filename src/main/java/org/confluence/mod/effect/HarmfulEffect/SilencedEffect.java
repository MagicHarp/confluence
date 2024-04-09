package org.confluence.mod.effect.HarmfulEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import org.confluence.mod.item.magic.IMagicAttack;

public class SilencedEffect extends MobEffect { //沉默 禁用使用魔力的物品
    public SilencedEffect() {
        super(MobEffectCategory.HARMFUL, 0xFFFAFA);
    }

    public static void onAdd(LivingEntity entity, LivingEntityUseItemEvent event) {
        if (entity instanceof Player && !entity.isSpectator() && entity.isUsingItem() && event.getItem().getItem() instanceof IMagicAttack) {
            event.setCanceled(true);
        }
    }
}
