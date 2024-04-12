package org.confluence.mod.effect.HarmfulEffect;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class ConfusedEffect extends MobEffect { //困惑 反向移动
    public ConfusedEffect() {
        super(MobEffectCategory.HARMFUL, 0x8B008B);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity living, int amplifier) {
        if (living instanceof LocalPlayer localPlayer) {
            localPlayer.xxa *= -1;
            localPlayer.yya *= -1;
            localPlayer.zza *= -1;
        } else {
            living.xxa *= -1;
            living.yya *= -1;
            living.zza *= -1;
        }
    }
}
