package org.confluence.mod.client.renderer.block;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import org.confluence.mod.block.entity.ActuatorsBlockEntity;
import org.confluence.mod.block.functional.StateProperties;
import org.jetbrains.annotations.NotNull;

public class ActuatorsBlockRenderer implements BlockEntityRenderer<ActuatorsBlockEntity> {
    private final BlockRenderDispatcher dispatcher;
    private final ModelBlockRenderer renderer;

    public ActuatorsBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.dispatcher = context.getBlockRenderDispatcher();
        this.renderer = dispatcher.getModelRenderer();
    }

    @Override
    public void render(@NotNull ActuatorsBlockEntity blockEntity, float delta, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int light, int packedOverlay) {
        poseStack.pushPose();
        BlockState target = blockEntity.getContain();
        BakedModel model = dispatcher.getBlockModel(target);
        RenderType renderType;
        if (blockEntity.getLevel() == null) {
            renderType = RenderType.solid();
        } else {
            renderType = model.getRenderTypes(target, blockEntity.getLevel().random, ModelData.EMPTY).asList().get(0);
        }
        float l = blockEntity.getBlockState().getValue(StateProperties.DRIVE) ? 0.5F : 1.0F;
        renderer.renderModel(poseStack.last(), multiBufferSource.getBuffer(renderType), null, model, 1.0F, 1.0F, 1.0F, (int) (light * l), OverlayTexture.NO_OVERLAY, ModelData.EMPTY, renderType);
        poseStack.popPose();
    }
}
