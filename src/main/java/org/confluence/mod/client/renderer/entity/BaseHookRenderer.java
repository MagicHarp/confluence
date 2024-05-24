package org.confluence.mod.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.client.model.entity.BaseHookModel;
import org.confluence.mod.entity.hook.BaseHookEntity;
import org.jetbrains.annotations.NotNull;

import static org.confluence.mod.Confluence.MODID;

public class BaseHookRenderer extends EntityRenderer<BaseHookEntity> {
    private static final ResourceLocation[] TEXTURE = new ResourceLocation[]{
        new ResourceLocation(MODID, "textures/entity/hook/grappling_hook.png")
    };
    private static final BlockState CHAIN = Blocks.CHAIN.defaultBlockState();
    private final BaseHookModel model;
    private final BlockRenderDispatcher dispatcher;

    public BaseHookRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new BaseHookModel(pContext.bakeLayer(BaseHookModel.LAYER_LOCATION));
        this.dispatcher = pContext.getBlockRenderDispatcher();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BaseHookEntity pEntity) {
        return TEXTURE[pEntity.getVariant().ordinal()];
    }

    public boolean shouldRender(BaseHookEntity entity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        if (super.shouldRender(entity, pCamera, pCamX, pCamY, pCamZ)) {
            return true;
        } else {
            Entity owner = entity.getOwner();
            if (owner != null) {
                Vec3 vec3 = owner.position().add(0.0, owner.getBbHeight() * 0.65, 0.0);
                Vec3 vec31 = entity.position().add(0.0, 0.25, 0.0);
                return pCamera.isVisible(new AABB(vec31.x, vec31.y, vec31.z, vec3.x, vec3.y, vec3.z));
            }
        }
        return false;
    }

    private Vec3 getPosition(Entity entity, double pYOffset, float pPartialTick) {
        double x = Mth.lerp(pPartialTick, entity.xOld, entity.getX());
        double y = Mth.lerp(pPartialTick, entity.yOld, entity.getY()) + pYOffset;
        double z = Mth.lerp(pPartialTick, entity.zOld, entity.getZ());
        return new Vec3(x, y, z);
    }

    @Override
    public void render(BaseHookEntity entity, float entityYaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int packedLight) {
        int skyLight = LightTexture.pack(15, LightTexture.sky(packedLight));
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(entity.getYRot() - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(entity.getXRot()));
        model.renderToBuffer(poseStack, multiBufferSource.getBuffer(model.renderType(getTextureLocation(entity))), skyLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
        Entity owner = entity.getOwner();
        if (owner != null) {
            poseStack.pushPose();
            Vec3 vec3 = getPosition(owner, owner.getBbHeight() * 0.65, partialTick);
            Vec3 vec31 = getPosition(entity, 0.25, partialTick);
            Vec3 vec32 = vec3.subtract(vec31).normalize();
            float f5 = (float) Math.acos(vec32.y);
            float f6 = (float) Math.atan2(vec32.z, vec32.x);
            poseStack.mulPose(Axis.YP.rotationDegrees((Mth.HALF_PI - f6) * Mth.RAD_TO_DEG));
            poseStack.mulPose(Axis.XP.rotationDegrees(f5 * Mth.RAD_TO_DEG));
            poseStack.translate(-0.5, 0.25, -0.75);
            int distance = Math.round(entity.distanceTo(owner));
            for (int i = 0; i < distance; i++) {
                dispatcher.renderSingleBlock(CHAIN, poseStack, multiBufferSource, skyLight, OverlayTexture.NO_OVERLAY);
                poseStack.translate(0.0, 1.0, 0.0);
            }
            poseStack.popPose();
        }
    }
}
