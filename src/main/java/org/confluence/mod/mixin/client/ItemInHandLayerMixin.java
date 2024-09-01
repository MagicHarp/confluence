package org.confluence.mod.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.util.DeathAnimOptions;
import org.confluence.mod.util.DeathAnimUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemInHandLayer.class)
public abstract class ItemInHandLayerMixin<T extends LivingEntity, M extends EntityModel<T> & ArmedModel> {
    @Unique private RenderLayerParent<T, M> confluence$renderer;
    @Unique private float confluence$partialTicks;

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"))
    private void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci){
        confluence$partialTicks = pPartialTicks;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void constr(RenderLayerParent<T, M> pRenderer, ItemInHandRenderer pItemInHandRenderer, CallbackInfo ci){
        confluence$renderer = pRenderer;
    }

    @Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V"))
    private void renderArmWithItem(LivingEntity pLivingEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, HumanoidArm pArm, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci){
        if(confluence$renderer instanceof LivingEntityRenderer<?,?> renderer){
            List<ModelPart> parts = DeathAnimUtils.findAllModelPart(renderer);
            ModelPart part = parts.size() > 1 ? parts.get(1) : parts.isEmpty() ? null : parts.get(0);
            DeathAnimOptions options = DeathAnimUtils.getDeathAnimOptions(pLivingEntity);
            DeathAnimUtils.moveParts(pPoseStack, pLivingEntity, part, confluence$partialTicks, options);
        }
    }
}
