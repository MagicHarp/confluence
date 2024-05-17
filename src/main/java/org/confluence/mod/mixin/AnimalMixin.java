package org.confluence.mod.mixin;

import net.minecraft.world.entity.animal.Animal;
import org.confluence.mod.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Animal.class)
public abstract class AnimalMixin {
    @Inject(method = "isInLove", at = @At("RETURN"), cancellable = true)
    private void inLove(CallbackInfoReturnable<Boolean> cir) {
        if (((Animal) (Object) this).hasEffect(ModEffects.LOVE.get())) {
            cir.setReturnValue(true);
        }
    }
}
