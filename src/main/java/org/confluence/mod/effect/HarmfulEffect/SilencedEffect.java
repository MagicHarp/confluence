package org.confluence.mod.effect.HarmfulEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.magic.IMagicAttack;

public class SilencedEffect extends MobEffect { //沉默 禁用使用魔力的物品
    public SilencedEffect() {
        super(MobEffectCategory.HARMFUL, 0xFFFAFA);
    }

    public static void apply(LivingEntity entity, PlayerInteractEvent.RightClickItem event) {
        if (!entity.isSpectator() && entity.isUsingItem() && event.getItemStack().getItem() instanceof IMagicAttack && entity.hasEffect(ModEffects.SILENCED.get())) {
            event.setCanceled(true);
        }
    }
}
