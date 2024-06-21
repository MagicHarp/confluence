package org.confluence.mod.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.model.entity.MoneyHoleModel;
import org.confluence.mod.entity.MoneyHoleEntity;
import org.jetbrains.annotations.NotNull;

import static org.confluence.mod.client.renderer.entity.hook.AbstractHookRenderer.getPosition;

public class MoneyHoleRenderer extends EntityRenderer<MoneyHoleEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Confluence.MODID, "textures/entity/money_hole.png");
    private final MoneyHoleModel model;

    public MoneyHoleRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new MoneyHoleModel(pContext.bakeLayer(MoneyHoleModel.LAYER_LOCATION));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull MoneyHoleEntity pEntity) {
        return TEXTURE;
    }

    @Override
    public void render(@NotNull MoneyHoleEntity pEntity, float pEntityYaw, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            Vec3 vec3 = getPosition(player, player.getBbHeight() * 0.85, pPartialTick);
            Vec3 vec31 = getPosition(pEntity, 0.5, pPartialTick);
            Vec3 vec32 = vec3.subtract(vec31).normalize();
            pPoseStack.mulPose(Axis.YP.rotation(Mth.HALF_PI - (float) Math.atan2(vec32.z, vec32.x)));
            pPoseStack.mulPose(Axis.XP.rotation(Mth.HALF_PI + (float) Math.acos(vec32.y)));
        }
        model.renderToBuffer(pPoseStack, pBuffer.getBuffer(model.renderType(TEXTURE)), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
    }
}
