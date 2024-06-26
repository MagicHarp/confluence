package org.confluence.mod.client.renderer.block;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.confluence.mod.block.functional.ActuatorsBlock;
import org.jetbrains.annotations.NotNull;

public class ActuatorsBlockRenderer extends MechanicalBlockRenderer<ActuatorsBlock.Entity> {
    private final BlockRenderDispatcher dispatcher;

    public ActuatorsBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        this.dispatcher = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(@NotNull ActuatorsBlock.Entity blockEntity, float delta, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int light, int packedOverlay) {
        super.render(blockEntity, delta, poseStack, multiBufferSource, light, packedOverlay);
        dispatcher.renderSingleBlock(blockEntity.getContain(), poseStack, multiBufferSource, light, OverlayTexture.NO_OVERLAY);
    }
}
