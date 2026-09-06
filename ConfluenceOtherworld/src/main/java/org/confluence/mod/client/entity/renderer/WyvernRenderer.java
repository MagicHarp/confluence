package org.confluence.mod.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.entity.monster.BaseWormMonster;
import org.confluence.mod.common.entity.monster.BaseWormPart;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;

/// 飞龙头部渲染器。飞龙模型同时保存了头部和三种体节，因此渲染头部时必须隐藏所有体节分组。
public final class WyvernRenderer<T extends BaseWormMonster> extends GeoNormalRenderer<T> {
    public WyvernRenderer(EntityRendererProvider.Context context, float scale) {
        super(context, Confluence.asResource("wyvern"), false, scale, 0.0F);
    }

    @Override
    protected void applyRotations(T wyvern, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        BaseWormPart first = wyvern.level().getEntitiesOfClass(BaseWormPart.class,
                        wyvern.getBoundingBox().inflate(8.0),
                        part -> !part.isRemoved() && part.getSegmentIndex() == 1 && part.getOwner() == wyvern)
                .stream().findFirst().orElse(null);
        Vec3 tangent = first == null ? Vec3.ZERO : wyvern.getPosition(partialTick).subtract(first.getPosition(partialTick));
        float yaw = Mth.rotLerp(partialTick, wyvern.yRotO, wyvern.getYRot());
        float pitch = Mth.rotLerp(partialTick, wyvern.xRotO, wyvern.getXRot());
        if (tangent.lengthSqr() > 1.0E-7) {
            if (tangent.horizontalDistanceSqr() > 1.0E-7) {
                yaw = (float) (Mth.atan2(tangent.z, tangent.x) * Mth.RAD_TO_DEG) - 90.0F;
            }
            pitch = (float) (-Mth.atan2(tangent.y, tangent.horizontalDistance()) * Mth.RAD_TO_DEG);
        }
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(-pitch));
    }

    @Override
    public void renderRecursively(PoseStack poseStack, T wyvern, GeoBone bone,
                                  RenderType renderType, MultiBufferSource buffers, VertexConsumer buffer,
                                  boolean reRender, float partialTick, int packedLight, int packedOverlay,
                                  float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        if (bone.getName().equals("Bone")) {
            // 头部主块为 [-4, 0, -6] + [8, 9, 11]，以主块中心而非颈端对齐轨迹。
            poseStack.translate(0.0, -4.5 / 16.0, 0.5 / 16.0);
        }
        super.renderRecursively(poseStack, wyvern, bone, renderType, buffers, buffer, reRender,
                partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }

    @Override
    protected boolean usesInterpolatedLight(T wyvern) {
        return true;
    }

    @Override
    public void preRender(
            PoseStack poseStack,
            T wyvern,
            BakedGeoModel model,
            MultiBufferSource buffers,
            VertexConsumer buffer,
            boolean reRender,
            float partialTick,
            int packedLight,
            int packedOverlay,
            float red,
            float green,
            float blue,
            float alpha) {
        model.getBone("Bone").ifPresent(bone -> bone.setHidden(false));
        model.getBone("Bone2").ifPresent(bone -> bone.setHidden(true));
        model.getBone("Bone3").ifPresent(bone -> bone.setHidden(true));
        model.getBone("Bone4").ifPresent(bone -> bone.setHidden(true));
        super.preRender(poseStack, wyvern, model, buffers, buffer, reRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
