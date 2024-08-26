package org.confluence.mod.mixin.client.accessor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LevelRenderer.class)
public interface LevelRendererAccessor {
    @Invoker
    static void callRenderShape(PoseStack pPoseStack, VertexConsumer pConsumer, VoxelShape pShape, double pX, double pY, double pZ, float pRed, float pGreen, float pBlue, float pAlpha){}

    @Accessor
    ObjectArrayList<LevelRenderer.RenderChunkInfo> getRenderChunksInFrustum();

    @Accessor
    double getXTransparentOld();

    @Accessor
    void setXTransparentOld(double xTransparentOld);

    @Accessor
    double getYTransparentOld();

    @Accessor
    void setYTransparentOld(double yTransparentOld);

    @Accessor
    double getZTransparentOld();

    @Accessor
    void setZTransparentOld(double zTransparentOld);

    @Accessor
    ChunkRenderDispatcher getChunkRenderDispatcher();
}
