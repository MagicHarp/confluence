package org.confluence.mod.mixin.integration;

import com.lowdragmc.shimmer.forge.platform.ForgePlatformHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ForgePlatformHelper.class)
public abstract class ForgePlatformHelperMixin {
    @Inject(method = "enableBuildinSetting", at = @At("RETURN"), cancellable = true)
    private static void disable(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
