package org.confluence.mod.effect.HarmfulEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class AcidVenomEffect extends MobEffect {    //酸性毒液：缓慢失去生命 每秒损失1点生命
    public AcidVenomEffect() {
        super(MobEffectCategory.HARMFUL, 0x228B22);
    }

    public static void onAdd(LivingEntity entity) {
        if (entity instanceof Player && entity.getHealth() > 1.0F) {
            entity.hurt(entity.damageSources().magic(), 1);
        }
    }
}
