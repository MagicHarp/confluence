package org.confluence.mod.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.model.entity.FlailModel;
import org.confluence.mod.client.renderer.entity.hook.AbstractHookRenderer;
import org.confluence.mod.entity.projectile.FlailEntity;
import org.jetbrains.annotations.NotNull;

public class FlailRenderer extends AbstractHookRenderer<FlailEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Confluence.MODID, "textures/entity/flail.png");

    public FlailRenderer(EntityRendererProvider.Context pContext){
        super(pContext, new FlailModel(pContext.bakeLayer(FlailModel.LAYER_LOCATION)));
    }

    //TODO
    @Override
    public BlockState getChain(FlailEntity entity){
        return Blocks.CHAIN.defaultBlockState();
    }

    // TODO: 每种连枷用不同的贴图
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FlailEntity pEntity){
        return TEXTURE;
    }

    @Override
    public void render(FlailEntity entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int packedLight){
        Entity owner = entity.getOwner();
        if(owner == null) return;
        if(entity.getPhase() == FlailEntity.PHASE_SPIN){
            entity.setYRot(-Mth.rotLerp(partialTick, owner.yRotO, owner.getYRot()));
            poseStack.pushPose();
            Vec3 pos = calculatePos(entity, owner);
            poseStack.translate(pos.x, pos.y, pos.z);
            renderHook(entity, poseStack, multiBufferSource, packedLight);
            entity.frameCount++;
            poseStack.popPose();
        }else {
            Vec3 pt = entity.position().vectorTo(owner.position());
            float rot = (float) Mth.wrapDegrees(Math.toDegrees(Mth.atan2(pt.x, pt.z)));
            poseStack.mulPose(Axis.YP.rotationDegrees(rot - 90.0F));
            model.renderToBuffer(poseStack, multiBufferSource.getBuffer(model.renderType(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
//            entity.setYRot(rot);
//            Confluence.LOGGER.info("{}", rot);
//            renderHook(entity, poseStack, multiBufferSource, packedLight);

        }
        // TODO: 链条
    }

    public Vec3 calculatePos(FlailEntity flail, Entity owner){
        double semiMajorAxis = 1.5;
        double semiMinorAxis = 1.5;
        double offsetY = semiMinorAxis / 2.0;
        float angle = (float) (2 * Math.PI * flail.frameCount / 25);
        float ownerYRot = owner.getYRot();
        float radians = (float) Math.toRadians(ownerYRot);
        double xPos = semiMajorAxis * Mth.cos(angle) * Mth.sin(radians) + 0.25 * Mth.sin(radians);
        double yPos = offsetY * Mth.sin(angle) + 1;
        double zPos = semiMajorAxis * Mth.cos(angle) * Mth.cos(radians) + 0.25 * Mth.cos(radians);
        return new Vec3(xPos, yPos, -zPos);
    }

}
