package org.confluence.mod.mixin.shimmer;

import com.lowdragmc.shimmer.forge.platform.ForgePlatformHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ForgePlatformHelper.class, remap = false)
public abstract class ForgePlatformHelperMixin {
    @Inject(method = "enableBuildinSetting", at = @At("RETURN"), cancellable = true)
    private void disable(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

    @Inject(method = "isDevelopmentEnvironment", at = @At("RETURN"), cancellable = true)
    private void isNot(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
