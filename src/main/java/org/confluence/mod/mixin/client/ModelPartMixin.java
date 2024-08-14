package org.confluence.mod.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.mixinauxiliary.IModelPart;
import org.confluence.mod.util.DeathAnimUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelPart.class)
public abstract class ModelPartMixin implements IModelPart {
    @Unique private LivingEntity confluence$renderingLiving;
    @Unique private float confluence$renderingPartialTick;

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V", at = @At("HEAD"))
    private void renderStart(PoseStack pPoseStack, VertexConsumer pVertexConsumer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha, CallbackInfo ci){
        pPoseStack.pushPose();
        if(confluence$renderingLiving != null){
            DeathAnimUtils.moveParts(pPoseStack, confluence$renderingLiving, this, confluence$renderingPartialTick);
        }
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V", at = @At("RETURN"))
    private void renderEnd(PoseStack pPoseStack, VertexConsumer pVertexConsumer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha, CallbackInfo ci){
        pPoseStack.popPose();
        confluence$renderingLiving = null;
    }

    @Override
    public void confluence$setRenderingLiving(LivingEntity living){
        confluence$renderingLiving = living;
    }

    @Override
    public void confluence$setRenderingPartialTick(float partialTick){
        confluence$renderingPartialTick = partialTick;
    }
}
