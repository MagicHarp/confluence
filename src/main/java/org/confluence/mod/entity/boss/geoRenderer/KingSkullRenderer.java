package org.confluence.mod.entity.boss.geoRenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.confluence.mod.entity.boss.geoEntity.KingSkull;
import org.confluence.mod.entity.boss.geoModel.KingSkullModel;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class KingSkullRenderer extends GeoEntityRenderer<KingSkull> {
    public KingSkullRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new KingSkullModel());
    }

    @Override
    public void preRender(PoseStack poseStack, KingSkull animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red,green,blue,alpha);
        poseStack.scale(3,3,3);
    }


/*
    @Override
    public @Nullable RenderType getRenderType(kulouwang animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.energySwirl(ResourceLocation.fromNamespaceAndPath(NeoMod.MODID,"texture/entity/creeper.png"), (float) Math.sin( partialTick),(float) Math.sin( partialTick));
    }
    */
}