package org.confluence.mod.client.renderer.block;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.data.ModelData;
import org.confluence.mod.block.entity.ActuatorsBlockEntity;
import org.jetbrains.annotations.NotNull;

public class ActuatorsBlockRenderer implements BlockEntityRenderer<ActuatorsBlockEntity> {
    private final BlockRenderDispatcher dispatcher;
    private final ModelBlockRenderer renderer;

    public ActuatorsBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.dispatcher = context.getBlockRenderDispatcher();
        this.renderer = dispatcher.getModelRenderer();
    }

    @Override
    public void render(@NotNull ActuatorsBlockEntity actuatorsBlockEntity, float delta, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int light, int packedOverlay) {
        poseStack.pushPose();
        BakedModel model = dispatcher.getBlockModel(actuatorsBlockEntity.getContain());
        renderer.renderModel(poseStack.last(), multiBufferSource.getBuffer(RenderType.translucent()), null, model, 1.0F, 1.0F, 1.0F, light, packedOverlay, ModelData.EMPTY, null);
        poseStack.popPose();
    }

    @Override
    public boolean shouldRender(@NotNull ActuatorsBlockEntity actuatorsBlockEntity, @NotNull Vec3 vec3) {
        return BlockEntityRenderer.super.shouldRender(actuatorsBlockEntity, vec3);
    }
}
