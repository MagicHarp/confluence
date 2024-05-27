package org.confluence.mod.mixin.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.confluence.mod.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin {
    @ModifyVariable(method = "shoot", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private float boost(float velocity) {
        if (((AbstractArrow) (Object) this).getOwner() instanceof LivingEntity living && living.hasEffect(ModEffects.ARCHERY.get())) {
            return velocity * 1.2F;
        }
        return velocity;
    }
}
