package org.confluence.mod.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CarriedBlockLayer;
import net.minecraft.world.entity.monster.EnderMan;
import org.confluence.mod.util.DeathAnimOptions;
import org.confluence.mod.util.DeathAnimUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CarriedBlockLayer.class)
public abstract class CarriedBlockLayerMixin {
    @Unique private RenderLayerParent<EnderMan, EndermanModel<EnderMan>> confluence$renderer;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void constr(RenderLayerParent<EnderMan, EndermanModel<EnderMan>> pRenderer, BlockRenderDispatcher pBlockRenderer, CallbackInfo ci){
        confluence$renderer = pRenderer;
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/monster/EnderMan;FFFFFF)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V"))
    private void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, EnderMan pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci){
        if(confluence$renderer instanceof LivingEntityRenderer<?, ?> renderer){
            List<ModelPart> parts = DeathAnimUtils.findAllModelPart(renderer);
            ModelPart part = parts.size() > 1 ? parts.get(1) : parts.isEmpty() ? null : parts.get(0);
            DeathAnimOptions options = DeathAnimUtils.getDeathAnimOptions(pLivingEntity);
            DeathAnimUtils.moveParts(pPoseStack, pLivingEntity, part, pPartialTicks, options);
        }
    }
}
