package org.confluence.mod.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.item.curio.movement.IJumpBoost;
import org.confluence.mod.util.LivingMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements LivingMixin {
    @Unique
    private double c$maxBoost = 1.0;

    @Unique
    public void c$freshMaxBoost() {
        this.c$maxBoost = IJumpBoost.getMaxBoost((LivingEntity) (Object) this);
    }

    @Inject(method = "getJumpPower", at = @At("RETURN"), cancellable = true)
    private void multiY(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue((float) (cir.getReturnValue() * c$maxBoost));
    }
}
