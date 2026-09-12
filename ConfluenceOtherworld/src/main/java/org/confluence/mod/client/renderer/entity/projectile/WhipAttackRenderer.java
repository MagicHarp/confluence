package org.confluence.mod.client.renderer.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.api.whip.WhipAppearance;
import org.confluence.mod.api.whip.WhipSegment;
import org.confluence.mod.client.renderer.entity.TetherRenderHelper;
import org.confluence.mod.common.entity.projectile.whip.WhipAttackEntity;
import org.confluence.mod.common.item.whip.BaseWhipItem;
import org.joml.Matrix3f;
import org.joml.Quaternionf;

import java.util.List;

/// 沿攻击实体的曲线绘制可组合鞭子外观。
///
/// 当前挥动时隐藏物品模型，鞭身通过通用手部连接点接到保留的手臂。
/// 每个外观分段独立选择固定像素间距或固定数量，并按声明顺序叠加；鞭梢和颜色线均为
/// 可选项。所有模型仍通过原版烘焙模型渲染，因此模型自身的面与背面剔除规则不会丢失。
public final class WhipAttackRenderer extends EntityRenderer<WhipAttackEntity> {
    /// 与 1.21 鞭子渲染器保持一致，物品模型按半格比例绘制。
    private static final float MODEL_SCALE = 0.5F;
    /// 模型经过 0.5 缩放后，一个 JSON 像素只占世界中的 1/32 格。
    /// 采样距离必须使用同一换算，否则四像素长的鞭节之间会留下半段空隙。
    private static final double RENDERED_PIXELS_PER_BLOCK = 32.0;

    public WhipAttackRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(WhipAttackEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int packedLight) {
        ItemStack weapon = entity.weapon();
        if (entity.isRemoved() || entity.swingProgress(partialTick) >= 1.0F) return;
        List<Vec3> points = entity.sampleWorldPoints(partialTick);
        if (weapon.isEmpty() || points.size() < 2) {
            return;
        }
        if (!(weapon.getItem() instanceof BaseWhipItem whip)) {
            return;
        }
        if (points.get(0).distanceToSqr(points.get(points.size() - 1)) < 1.0E-6) return;
        if (entity.getOwner() instanceof Player player) {
            Vec3 offset = TetherRenderHelper.handPosition(entityRenderDispatcher, player, entity.attackArm(), partialTick).subtract(points.get(0));
            // 整体平移到手臂连接点，不再扭曲近手端或改变鞭身形状。
            points = points.stream().map(point -> point.add(offset)).toList();
        }
        WhipAppearance appearance = whip.appearance();
        Vec3 origin = entity.getPosition(partialTick);
        Vec3 widthAxis = entity.swingRight();

        for (WhipSegment segment : appearance.segments()) {
            renderLayer(weapon, segment, points, origin, widthAxis, poseStack, buffers, packedLight);
        }
        if (appearance.optionalLineColor().isPresent()) {
            renderCurveLine(points, origin, appearance.optionalLineColor().getAsInt(), poseStack, buffers);
        }
        super.render(entity, entityYaw, partialTick, poseStack, buffers, packedLight);
    }


    private static void renderLayer(ItemStack weapon, WhipSegment segment, List<Vec3> curve, Vec3 origin, Vec3 widthAxis, PoseStack poseStack, MultiBufferSource buffers, int packedLight) {
        List<WhipPolylineSamples.Sample> samples =
                segment.mode() == WhipSegment.Mode.FIXED_SPACING
                        ? WhipPolylineSamples.fixedSpacing(curve, segment.value() / RENDERED_PIXELS_PER_BLOCK)
                        : WhipPolylineSamples.fixedCount(curve, segment.value());
        BakedModel body = WhipSegmentModels.model(segment.model());
        boolean hasTip = segment.tipModel() != null;
        WhipPolylineSamples.Sample tip = hasTip
                ? WhipPolylineSamples.tip(curve)
                : null;
        int bodyCount = samples.size();
        if (hasTip
                && bodyCount > 0
                && samples.get(bodyCount - 1).position()
                .distanceToSqr(tip.position()) <= 1.0E-10) {
            bodyCount--;
        }
        Quaternionf orientation = null;
        Vec3 previousDirection = null;
        // 沿曲线传递上一节的朝向，不再为每节叠加固定扭转。
        for (int index = 0; index < bodyCount; index++) {
            WhipPolylineSamples.Sample sample = samples.get(index);
            Vec3 direction = sample.tangent().scale(-1.0);
            orientation = segmentOrientation(direction, previousDirection, orientation, widthAxis);
            renderSegment(weapon, body, sample.position().subtract(origin), orientation, poseStack, buffers, packedLight);
            previousDirection = direction;
        }
        if (hasTip) {
            orientation = segmentOrientation(tip.tangent().scale(-1.0), previousDirection, orientation, widthAxis);
            renderSegment(weapon, WhipSegmentModels.model(segment.tipModel()), tip.position().subtract(origin), orientation, poseStack, buffers, packedLight);
        }
    }

    private static Quaternionf segmentOrientation(Vec3 direction, Vec3 previousDirection, Quaternionf previous, Vec3 widthAxis) {
        if (previous != null && previousDirection.dot(direction) > -0.9999) {
            return new Quaternionf().rotationTo((float) previousDirection.x, (float) previousDirection.y, (float) previousDirection.z, (float) direction.x, (float) direction.y, (float) direction.z).mul(previous).normalize();
        }
        Vec3 width = widthAxis.subtract(direction.scale(widthAxis.dot(direction)));
        if (width.lengthSqr() < 1.0E-6) {
            Vec3 fallback = Math.abs(direction.y) < 0.9 ? new Vec3(0.0, 1.0, 0.0) : new Vec3(0.0, 0.0, 1.0);
            width = fallback.subtract(direction.scale(fallback.dot(direction)));
        }
        width = width.normalize();
        Vec3 normal = width.cross(direction).normalize();
        return new Quaternionf().setFromNormalized(new Matrix3f((float) width.x, (float) width.y, (float) width.z, (float) direction.x, (float) direction.y, (float) direction.z, (float) normal.x, (float) normal.y, (float) normal.z));
    }

    private static void renderSegment(ItemStack weapon, BakedModel model, Vec3 offset, Quaternionf orientation, PoseStack poseStack, MultiBufferSource buffers, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(offset.x, offset.y, offset.z);
        poseStack.scale(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE);

        // 模型沿 +Y 延伸，采样点位于该节末端，朝根部排布；先将模型中轴移至原点。
        poseStack.mulPose(orientation);
        poseStack.translate(-0.5F, 0.0F, -0.5F);

        for (RenderType renderType : model.getRenderTypes(weapon, false)) {
            VertexConsumer consumer = ItemRenderer.getFoilBuffer(buffers, renderType, false, weapon.hasFoil());
            Minecraft.getInstance().getItemRenderer().renderModelLists(model, weapon, packedLight, OverlayTexture.NO_OVERLAY, poseStack, consumer);
        }
        poseStack.popPose();
    }

    private static void renderCurveLine(List<Vec3> points, Vec3 origin, int color, PoseStack poseStack, MultiBufferSource buffers) {
        VertexConsumer consumer = buffers.getBuffer(RenderType.lineStrip());
        PoseStack.Pose pose = poseStack.last();
        for (int index = 0; index < points.size(); index++) {
            Vec3 point = points.get(index).subtract(origin);
            Vec3 tangent;
            if (index + 1 < points.size()) {
                tangent = points.get(index + 1).subtract(points.get(index));
            } else {
                tangent = points.get(index).subtract(points.get(index - 1));
            }
            if (tangent.lengthSqr() <= 1.0E-10) {
                continue;
            }
            tangent = tangent.normalize();
            consumer.vertex(pose.pose(), (float) point.x, (float) point.y, (float) point.z)
                    .color(color)
                    .normal(pose.normal(), (float) tangent.x, (float) tangent.y, (float) tangent.z)
                    .endVertex();
        }
    }

    @Override
    public boolean shouldRender(WhipAttackEntity entity, Frustum frustum, double cameraX, double cameraY, double cameraZ) {
        // 实体锚点在玩家手边，但鞭梢可能进入视锥，因此不能只按实体本身的小包围盒裁剪。
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(WhipAttackEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
