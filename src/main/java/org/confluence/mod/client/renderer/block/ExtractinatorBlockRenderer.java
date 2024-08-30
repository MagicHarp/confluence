package org.confluence.mod.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.confluence.mod.block.functional.ExtractinatorBlock;
import org.confluence.mod.client.model.block.ExtractinatorBlockModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ExtractinatorBlockRenderer extends GeoBlockRenderer<ExtractinatorBlock.Entity> {
    public ExtractinatorBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new ExtractinatorBlockModel());
    }

    @Override
    public void defaultRender(PoseStack poseStack, ExtractinatorBlock.Entity animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        if (animatable.getBlockState().getValue(ExtractinatorBlock.PART) == ExtractinatorBlock.ExtractinatorPart.BASE) {
            super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
        }
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull ExtractinatorBlock.Entity pBlockEntity) {
        return true;
    }
}
