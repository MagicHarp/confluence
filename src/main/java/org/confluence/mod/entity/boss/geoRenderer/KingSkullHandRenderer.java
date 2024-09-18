package org.confluence.mod.entity.boss.geoRenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.confluence.mod.entity.boss.geoEntity.KingSkullHand;
import org.confluence.mod.entity.boss.geoModel.KingSkullHandModel;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class KingSkullHandRenderer extends GeoEntityRenderer<KingSkullHand> {
    public KingSkullHandRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new KingSkullHandModel());
    }

    @Override
    public void preRender(PoseStack poseStack, KingSkullHand animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red,green,blue,alpha);
        poseStack.scale(3,3,3);
        poseStack.translate(0,-0.5,0);

    }



/*
    @Override
    public @Nullable RenderType getRenderType(kulouwang animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.energySwirl(ResourceLocation.fromNamespaceAndPath(NeoMod.MODID,"texture/entity/creeper.png"), (float) Math.sin( partialTick),(float) Math.sin( partialTick));
    }
    */
}