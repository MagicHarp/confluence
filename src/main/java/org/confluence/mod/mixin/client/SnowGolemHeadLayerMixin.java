package org.confluence.mod.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.SnowGolemHeadLayer;
import net.minecraft.world.entity.animal.SnowGolem;
import org.confluence.mod.mixinauxiliary.ILivingEntity;
import org.confluence.mod.util.DeathAnimOptions;
import org.confluence.mod.util.DeathAnimUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowGolemHeadLayer.class)
public abstract class SnowGolemHeadLayerMixin extends RenderLayer<SnowGolem, SnowGolemModel<SnowGolem>> {
    private SnowGolemHeadLayerMixin(RenderLayerParent<SnowGolem, SnowGolemModel<SnowGolem>> pRenderer){
        super(pRenderer);
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/SnowGolem;FFFFFF)V",
        at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V",shift = At.Shift.AFTER))
    private void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, SnowGolem pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci){
        ModelPart head = this.getParentModel().getHead();
        ((ILivingEntity) pLivingEntity).confluence$getMotionsForPart(head);
        DeathAnimOptions options = DeathAnimUtils.getDeathAnimOptions(pLivingEntity);
        DeathAnimUtils.moveParts(pPoseStack, pLivingEntity, head, pPartialTicks, options);
    }
}
