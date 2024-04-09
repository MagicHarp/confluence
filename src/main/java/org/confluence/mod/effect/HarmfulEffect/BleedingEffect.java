package org.confluence.mod.effect.HarmfulEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHealEvent;

public class BleedingEffect extends MobEffect { //流血 不能自然恢复生命
    public BleedingEffect() {
        super(MobEffectCategory.HARMFUL, 0xA52A2A);
    }

    public static void onAdd(LivingEntity entity, LivingHealEvent event){
        if(entity instanceof Player && !entity.isSpectator()){
            if(event.getAmount() > 0){
                event.setCanceled(true);
            }
        }
    }
}
