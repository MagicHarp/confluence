package org.confluence.mod.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.block.crafting.AlchemyTableBlock;
import org.confluence.mod.client.color.FloatRGBA;
import org.confluence.mod.util.ModUtils;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class AlchemyTableBlockRenderer implements BlockEntityRenderer<AlchemyTableBlock.Entity> {
    public static final ResourceLocation BEAM_LOCATION = new ResourceLocation("textures/entity/beacon_beam.png");

    public AlchemyTableBlockRenderer(BlockEntityRendererProvider.Context context){}

    @Override
    public void render(AlchemyTableBlock.Entity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.translate(0, 1, 0);
        var consumer = pBuffer.getBuffer(RenderType.beaconBeam(BEAM_LOCATION, false));

        if (pBlockEntity.firstColor != 0){                                                                       //x长       y长          z长
            ModUtils.renderCube(pPoseStack, consumer, new FloatRGBA(0.5F, 0, 0, 0.05F),0.25F,0.4F,0.25F,0,0);
        }
        if (pBlockEntity.secondColor != 0){
            pPoseStack.translate(0.75, 0, 0.75);
            ModUtils.renderCube(pPoseStack, consumer, new FloatRGBA(0F, 0.5f, 0, 0.05F),0.25F,0.4F,0.25F,0,0);
        }

        pPoseStack.popPose();
    }
}
