package org.confluence.mod.common.effect.harmful;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

// todo
public class SilencedEffect extends MobEffect { //沉默 禁用使用魔力的物品
    public SilencedEffect() {
        super(MobEffectCategory.HARMFUL, 0xFFFAFA);
    }

//    public static void onRightClick(LivingEntity entity, PlayerInteractEvent.RightClickItem event) {
//        if (!entity.isSpectator() && entity.isUsingItem() && event.getItemStack().getItem() instanceof IManaWeapon && entity.hasEffect(ModEffects.SILENCED.get())) {
//            event.setCanceled(true);
//        }
//    }
}
