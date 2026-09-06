package org.confluence.mod.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.common.entity.monster.BaseWormMonster;
import org.confluence.mod.common.entity.monster.BaseWormPart;

/// 让普通蠕虫头部的模型接口始终朝向第一体节中心；移动朝向仍由服务端 AI 决定。
public class WormHeadRenderer<T extends BaseWormMonster> extends GeoNormalRenderer<T> {
    public WormHeadRenderer(EntityRendererProvider.Context context, ResourceLocation path, float scale) {
        super(context, path, true, scale, 0.0F);
    }

    @Override
    protected void applyRotations(T worm, PoseStack poseStack, float ageInTicks,
                                  float rotationYaw, float partialTick) {
        super.applyRotations(worm, poseStack, ageInTicks, getRenderYaw(worm, partialTick), partialTick);
    }

    @Override
    protected float getRenderYaw(T worm, float partialTick) {
        Vec3 tangent = chainTangent(worm, partialTick);
        return tangent.horizontalDistanceSqr() > 1.0E-7D
                ? (float) (Mth.atan2(tangent.z, tangent.x) * Mth.RAD_TO_DEG) - 90.0F
                : super.getRenderYaw(worm, partialTick);
    }

    @Override
    protected float getRenderPitch(T worm, float partialTick) {
        Vec3 tangent = chainTangent(worm, partialTick);
        return tangent.lengthSqr() > 1.0E-7D
                ? (float) (-Mth.atan2(tangent.y, tangent.horizontalDistance()) * Mth.RAD_TO_DEG)
                : super.getRenderPitch(worm, partialTick);
    }

    @Override
    protected boolean usesInterpolatedLight(T worm) {
        return true;
    }

    protected Vec3 chainTangent(T worm, float partialTick) {
        BaseWormPart first = worm.level().getEntitiesOfClass(BaseWormPart.class,
                        worm.getBoundingBox().inflate(4.0D),
                        part -> !part.isRemoved() && part.getSegmentIndex() == 1 && part.getOwner() == worm)
                .stream().findFirst().orElse(null);
        if (first == null) return Vec3.ZERO;
        Vec3 headCenter = worm.getPosition(partialTick).add(0.0D, worm.getBbHeight() * 0.5D, 0.0D);
        Vec3 firstCenter = first.getPosition(partialTick).add(0.0D, first.getBbHeight() * 0.5D, 0.0D);
        return headCenter.subtract(firstCenter);
    }
}
