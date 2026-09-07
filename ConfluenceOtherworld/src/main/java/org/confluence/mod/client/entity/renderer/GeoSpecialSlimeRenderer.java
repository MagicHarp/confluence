package org.confluence.mod.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.confluence.mod.common.entity.monster.slime.SpikedSlime;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;

/// 尖刺史莱姆的专用渲染器。
///
/// 史莱姆主体和外壳使用半透明材质，尖刺使用不透明裁剪材质；模型尺寸及挤压动画
/// 与普通史莱姆共用同一套同步状态，避免视觉尺寸和碰撞箱不一致。
public final class GeoSpecialSlimeRenderer<T extends SpikedSlime> extends GeoNormalRenderer<T> {
    public GeoSpecialSlimeRenderer(EntityRendererProvider.Context context, ResourceLocation path) {
        super(context, path);
    }

    @Override
    protected void adjustPose(PoseStack poseStack, T slime, BakedGeoModel model, float partialTick) {
        super.adjustPose(poseStack, slime, model, partialTick);
        float size = slime.getVisualSize();
        shadowRadius = 0.25F * size;
        poseStack.scale(0.999F, 0.999F, 0.999F);
        poseStack.translate(0.0F, 0.001F, 0.0F);
        float squish = Mth.lerp(partialTick, slime.getOldSquish(), slime.getSquish())
                / (size * 0.5F + 1.0F);
        float inverse = 1.0F / (squish + 1.0F);
        poseStack.scale(inverse * size, size / inverse, inverse * size);
    }

    @Override
    public void renderRecursively(PoseStack poseStack, T slime, GeoBone bone,
                                  RenderType renderType, MultiBufferSource buffers, VertexConsumer buffer,
                                  boolean reRender, float partialTick, int packedLight, int packedOverlay,
                                  float red, float green, float blue, float alpha) {
        if (!bone.getName().equals("outer") && !bone.getName().equals("slime")) {
            renderType = RenderType.entityCutout(getTextureLocation(slime));
            buffer = buffers.getBuffer(renderType);
        }
        super.renderRecursively(poseStack, slime, bone, renderType, buffers, buffer, reRender,
                partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public @Nullable RenderType getRenderType(T slime, ResourceLocation texture,
                                              @Nullable MultiBufferSource buffers, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(slime));
    }
}
