package org.confluence.mod.mixin;

import net.minecraft.world.item.Tiers;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Tiers.class)
public abstract class TiersMixin {
    @Shadow
    @Final
    private int uses;

    @Inject(method = "getUses", at = @At(value = "RETURN"), cancellable = true)
    private void modifyGold(CallbackInfoReturnable<Integer> cir) {
        if (uses == 32) cir.setReturnValue(1661);
    }
}
