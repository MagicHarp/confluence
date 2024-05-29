package org.confluence.mod.client.renderer.entity.fishing;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.client.model.entity.fshing.BaseFishingHookModel;
import org.confluence.mod.entity.fishing.BaseFishingHook;
import org.jetbrains.annotations.NotNull;

import static org.confluence.mod.Confluence.MODID;

public class BaseFishingHookRenderer extends EntityRenderer<BaseFishingHook> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
        new ResourceLocation(MODID, "textures/entity/fishing/wood.png"),
        new ResourceLocation(MODID, "textures/entity/fishing/reinforced.png"),
        new ResourceLocation(MODID, "textures/entity/fishing/souls.png")
    };
    private final BaseFishingHookModel[] MODELS;

    public BaseFishingHookRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.MODELS = new BaseFishingHookModel[]{
            new BaseFishingHookModel(pContext.bakeLayer(BaseFishingHookModel.WOOD)),
            new BaseFishingHookModel(pContext.bakeLayer(BaseFishingHookModel.REINFORCED)),
            new BaseFishingHookModel(pContext.bakeLayer(BaseFishingHookModel.SOULS))
        };
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(BaseFishingHook pEntity) {
        return TEXTURES[pEntity.getVariant().getId()];
    }

    @Override
    public void render(BaseFishingHook pEntity, float pEntityYaw, float pPartialTick, @NotNull PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        BaseFishingHookModel model = MODELS[pEntity.getVariant().getId()];
        model.renderToBuffer(pPoseStack, pBuffer.getBuffer(model.renderType(getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
