package org.confluence.mod.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.client.model.item.PhasebladeModel;
import org.confluence.mod.common.item.sword.BasePhasebladeItem;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class PhasebladeRenderer extends GeoItemRenderer<BasePhasebladeItem> {
    private final boolean forceBladeOn;
    private boolean bladeOn;

    public PhasebladeRenderer() {
        this(false);
    }

    public PhasebladeRenderer(boolean forceBladeOn) {
        super(new PhasebladeModel(forceBladeOn));
        this.forceBladeOn = forceBladeOn;
        addRenderLayer(new AutoGlowingGeoLayer<>(this) {
            @Override
            protected RenderType getRenderType(BasePhasebladeItem item) {
                return RenderType.eyes(item.emissiveResource());
            }

            @Override
            public void render(PoseStack poseStack, BasePhasebladeItem item, BakedGeoModel model, RenderType renderType,
                               MultiBufferSource buffers, VertexConsumer buffer, float partialTick,
                               int packedLight, int packedOverlay) {
                if (bladeOn)
                    super.render(poseStack, item, model, renderType, buffers, buffer, partialTick, packedLight, packedOverlay);
            }
        });
    }

    @Override
    public void actuallyRender(PoseStack poseStack, BasePhasebladeItem item, BakedGeoModel model, RenderType renderType,
                               MultiBufferSource buffers, VertexConsumer buffer, boolean isReRender,
                               float partialTick, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        bladeOn = forceBladeOn || BasePhasebladeItem.isTurnOn(getCurrentItemStack());
        super.actuallyRender(poseStack, item, model, renderType, buffers, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public @Nullable RenderType getRenderType(BasePhasebladeItem item, ResourceLocation texture, @Nullable MultiBufferSource buffers, float partialTick) {
        return RenderType.text(texture);
    }
}
