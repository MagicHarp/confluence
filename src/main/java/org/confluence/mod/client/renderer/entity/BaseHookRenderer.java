package org.confluence.mod.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.client.model.entity.BaseHookModel;
import org.confluence.mod.entity.hook.BaseHookEntity;
import org.jetbrains.annotations.NotNull;

import static org.confluence.mod.Confluence.MODID;

public class BaseHookRenderer extends EntityRenderer<BaseHookEntity> {
    private static final ResourceLocation[] TEXTURE = new ResourceLocation[]{
        new ResourceLocation(MODID, "textures/entity/bee_projectile.png") // todo 模型材质
    };
    private final BaseHookModel model;

    public BaseHookRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new BaseHookModel(pContext.bakeLayer(BaseHookModel.LAYER_LOCATION));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BaseHookEntity pEntity) {
        return TEXTURE[pEntity.getVariant().ordinal()];
    }

    @Override
    public void render(BaseHookEntity entity, float entityYaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int packedLight) {
        model.renderToBuffer(poseStack, multiBufferSource.getBuffer(model.renderType(getTextureLocation(entity))), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
