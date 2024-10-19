package org.confluence.mod.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import org.confluence.mod.client.post.PostUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;renderItemInHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/Camera;F)V"))
    public void renderLevelHeadMixin(float pPartialTicks, long pFinishTimeNano, PoseStack pPoseStack, CallbackInfo ci ){


    }

    @Inject(method = "renderLevel", at = @At("RETURN"))
    public void renderLevelReturnMixin(float pPartialTicks, long pFinishTimeNano, PoseStack pPoseStack, CallbackInfo ci ){
        PostUtil.bloom.apply();
        PostUtil.backUp();

    }


}
