package org.confluence.mod.mixin.client;

import net.minecraft.client.MouseHandler;
import org.confluence.mod.effect.beneficial.GravitationEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
    @ModifyArg(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"), index = 0)
    private double reverseX(double x) {
        return GravitationEffect.isShouldRot() ? x * -1.0 : x;
    }

    @ModifyArg(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"), index = 1)
    private double reverseY(double y) {
        return GravitationEffect.isShouldRot() ? y * -1.0 : y;
    }
}
