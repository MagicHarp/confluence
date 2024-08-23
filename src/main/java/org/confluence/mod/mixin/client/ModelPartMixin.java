package org.confluence.mod.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec2;
import org.confluence.mod.mixinauxiliary.IModelPart;
import org.confluence.mod.mixinauxiliary.SelfGetter;
import org.confluence.mod.util.DeathAnimOptions;
import org.confluence.mod.util.DeathAnimUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.confluence.mod.util.DeathAnimUtils.moveParts;

/** @author voila */
@Mixin(ModelPart.class)
public abstract class ModelPartMixin implements IModelPart, SelfGetter<ModelPart> {
    /** LivingRenderer在渲染死的生物开始前把实体数据写进来，null就是没死
     *  {@link org.confluence.mod.event.ForgeEvents#beforeRenderLiving}*/
    @Unique private LivingEntity confluence$renderingLiving;
    @Unique private float confluence$renderingPartialTick;
    @Unique private float[] confluence$parentOffset = new float[6];
    @Unique private ModelPart confluence$root;
    @Unique private Vec2 confluence$landMotion;
    @Unique private boolean confluence$isSkull = false;

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V", at = @At(value = "INVOKE",target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V"))
    private void renderStart(PoseStack pPoseStack, VertexConsumer pVertexConsumer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha, CallbackInfo ci){
        pPoseStack.pushPose();
        DeathAnimOptions options = DeathAnimUtils.getDeathAnimOptions(confluence$renderingLiving);
        if(confluence$renderingLiving != null && options != null){
            moveParts(pPoseStack, confluence$renderingLiving, this, confluence$renderingPartialTick, options);
        }
    }

    // TODO: 抵消父模型旋转

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V", at = @At(value = "INVOKE",target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V"))
    private void renderEnd(PoseStack pPoseStack, VertexConsumer pVertexConsumer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha, CallbackInfo ci){
        pPoseStack.popPose();
    }

    @Override
    public void confluence$setRenderingLiving(LivingEntity living){
        confluence$renderingLiving = living;
    }

    @Override
    public void confluence$setRenderingPartialTick(float partialTick){
        confluence$renderingPartialTick = partialTick;
    }

    @Override
    public float[] confluence$parentOffset(float... offset){
        if(offset.length > 0){
            confluence$parentOffset = offset;
        }
        return confluence$parentOffset;
    }

    @Override
    public ModelPart confluence$root(ModelPart... root){
        if(root.length > 0){
            confluence$root = root[0];
        }
        return confluence$root;
    }
    @Override
    public Vec2 confluence$landMotion(Vec2... m){
        if(m.length > 0){
            confluence$landMotion = m[0];
        }
        return confluence$landMotion;
    }

    @Override
    public boolean confluence$isSkull(boolean... isSkull){
        if(isSkull.length > 0){
            confluence$isSkull = isSkull[0];
        }
        return confluence$isSkull;
    }
}
