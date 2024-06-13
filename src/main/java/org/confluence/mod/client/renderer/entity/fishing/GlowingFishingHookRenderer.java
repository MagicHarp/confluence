package org.confluence.mod.client.renderer.entity.fishing;

import com.lowdragmc.shimmer.client.ShimmerRenderTypes;
import com.lowdragmc.shimmer.client.postprocessing.PostProcessing;
import com.lowdragmc.shimmer.client.shader.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.client.color.AnimateColor;
import org.confluence.mod.client.color.IntegerRGB;
import org.confluence.mod.client.model.entity.fishing.GlowingFishingHookModel;
import org.confluence.mod.entity.fishing.CurioFishingHook;
import org.jetbrains.annotations.NotNull;

import static org.confluence.mod.Confluence.MODID;
import static org.confluence.mod.client.renderer.entity.fishing.BaseFishingHookRenderer.renderString;

public class GlowingFishingHookRenderer extends EntityRenderer<CurioFishingHook> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
        new ResourceLocation(MODID, "textures/entity/fishing/common.png"),
        new ResourceLocation(MODID, "textures/entity/fishing/glowing.png"),
        new ResourceLocation(MODID, "textures/entity/fishing/lava_moss.png"),
        new ResourceLocation(MODID, "textures/entity/fishing/helium_moss.png"),
        new ResourceLocation(MODID, "textures/entity/fishing/neon_moss.png"),
        new ResourceLocation(MODID, "textures/entity/fishing/argon_moss.png"),
        new ResourceLocation(MODID, "textures/entity/fishing/krypton_moss.png"),
        new ResourceLocation(MODID, "textures/entity/fishing/xenon_moss.png")
    };
    private static final RenderType[] GLOWS = new RenderType[]{
        null,
        ShimmerRenderTypes.emissiveArmor(new ResourceLocation(MODID, "textures/entity/fishing/glowing_glow.png")),
        ShimmerRenderTypes.emissiveArmor(new ResourceLocation(MODID, "textures/entity/fishing/lava_moss_glow.png")),
        RenderType.entityCutoutNoCull(new ResourceLocation(MODID, "textures/entity/fishing/helium_moss_glow.png")),
        ShimmerRenderTypes.emissiveArmor(new ResourceLocation(MODID, "textures/entity/fishing/neon_moss_glow.png")),
        ShimmerRenderTypes.emissiveArmor(new ResourceLocation(MODID, "textures/entity/fishing/argon_moss_glow.png")),
        ShimmerRenderTypes.emissiveArmor(new ResourceLocation(MODID, "textures/entity/fishing/krypton_moss_glow.png")),
        ShimmerRenderTypes.emissiveArmor(new ResourceLocation(MODID, "textures/entity/fishing/xenon_moss_glow.png"))
    };
    private final GlowingFishingHookModel mossModel;
    private final GlowingFishingHookModel commonModel;
    private final GlowingFishingHookModel glowingModel;

    public GlowingFishingHookRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.mossModel = new GlowingFishingHookModel(pContext.bakeLayer(GlowingFishingHookModel.MOSS));
        this.commonModel = new GlowingFishingHookModel(pContext.bakeLayer(GlowingFishingHookModel.COMMON));
        this.glowingModel = new GlowingFishingHookModel(pContext.bakeLayer(GlowingFishingHookModel.GLOWING));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull CurioFishingHook pEntity) {
        return TEXTURES[pEntity.getVariant().getId()];
    }

    public GlowingFishingHookModel getModel(CurioFishingHook fishingHook) {
        CurioFishingHook.Variant variant = fishingHook.getVariant();
        if (variant == CurioFishingHook.Variant.COMMON) {
            return commonModel;
        } else if (variant == CurioFishingHook.Variant.GLOWING) {
            return glowingModel;
        } else {
            return mossModel;
        }
    }

    @Override
    public void render(@NotNull CurioFishingHook pEntity, float pEntityYaw, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        int id = pEntity.getVariant().getId();
        ResourceLocation texture = TEXTURES[id];
        GlowingFishingHookModel model = getModel(pEntity);
        model.renderToBuffer(pPoseStack, pBuffer.getBuffer(model.renderType(texture)), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        PoseStack finalStack = RenderUtils.copyPoseStack(pPoseStack);
        RenderType glow = GLOWS[id];
        if (glow != null) {
            float r, g, b;
            if (id == 3) { // Helium
                r = AnimateColor.getDiscoR() / 255.0F;
                g = AnimateColor.getDiscoG() / 255.0F;
                b = AnimateColor.getDiscoB() / 255.0F;
            } else {
                r = g = b = 1.0F;
            }
            PostProcessing.BLOOM_UNITY.postEntity(sourceConsumer -> model.renderToBuffer(finalStack, sourceConsumer.getBuffer(glow), 0xF000F0, OverlayTexture.NO_OVERLAY, r, g, b, 1.0F));
        }
        renderString(entityRenderDispatcher, pEntity, pPartialTick, pPoseStack, pBuffer, IntegerRGB.BLACK);
    }
}
