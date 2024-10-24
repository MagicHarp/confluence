package org.confluence.mod.common.init;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

import static org.confluence.mod.terra_curio.common.init.ModAttributes.MAGIC_DAMAGE;
import static org.confluence.mod.terra_curio.common.init.ModAttributes.hasCustomAttribute;

public final class ModAttributes { // todo invoke
    public static float applyMagicDamage(DamageSource damageSource, float amount) {
        if (hasCustomAttribute(MAGIC_DAMAGE)) return amount;
        if (damageSource.is(DamageTypes.MAGIC) || damageSource.is(DamageTypes.INDIRECT_MAGIC)) {
            if (damageSource.getEntity() instanceof LivingEntity living) {
                AttributeInstance attributeInstance = living.getAttribute(MAGIC_DAMAGE);
                if (attributeInstance == null) return amount;
                return amount * (float) attributeInstance.getValue();
            }
        }
        return amount;
    }
}
