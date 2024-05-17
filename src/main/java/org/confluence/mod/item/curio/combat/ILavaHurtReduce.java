package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.util.CuriosUtils;

public interface ILavaHurtReduce {
    static float apply(LivingEntity living, DamageSource damageSource, float amount) {
        if (damageSource.is(DamageTypes.LAVA) && CuriosUtils.hasCurio(living, ILavaHurtReduce.class)) {
            living.setSecondsOnFire(7);
            return amount * 0.5F;
        }
        return amount;
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.lava_hurt_reduce");
}
