package org.confluence.mod.mixin.client;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.effect.beneficial.GravitationEffect;
import org.confluence.mod.util.IEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Inject(method = "isEntityUpsideDown", at = @At("RETURN"), cancellable = true)
    private static void upsideDown(LivingEntity living, CallbackInfoReturnable<Boolean> cir) {
        if ((living instanceof LocalPlayer && GravitationEffect.isShouldRot()) || (living instanceof RemotePlayer && ((IEntity)living).c$isShouldRot())) {
            cir.setReturnValue(true);
        }
    }
}
