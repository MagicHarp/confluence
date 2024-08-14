package org.confluence.mod.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EyesLayer.class)
public abstract class EyesLayerMixin {
    @Inject(method = "render",at = @At("HEAD"),cancellable = true)
    private void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, Entity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci){
        if(pLivingEntity instanceof LivingEntity living && living.isDeadOrDying()){
            ci.cancel();
        }
    }
}
