package org.confluence.mod.mixin;

import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = CombatRules.class, priority = 2000)
public abstract class CombatRulesMixin {
    @ModifyArg(method = "getDamageAfterAbsorb", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F"), index = 2)
    private static float byPass(float pMax) {
        return (float) ((RangedAttribute) Attributes.ARMOR).getMaxValue();
    }
}
