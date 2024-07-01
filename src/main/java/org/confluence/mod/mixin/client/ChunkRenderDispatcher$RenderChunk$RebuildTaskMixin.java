package org.confluence.mod.mixin.client;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexSorting;
import net.minecraft.client.renderer.ChunkBufferBuilderPack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.core.BlockPos;
import org.confluence.mod.client.shader.ModRenderTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Set;

@Mixin(ChunkRenderDispatcher.RenderChunk.RebuildTask.class)
public abstract class ChunkRenderDispatcher$RenderChunk$RebuildTaskMixin {
    @Inject(method = "compile", at= @At(value = "INVOKE", target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z"),locals = LocalCapture.CAPTURE_FAILSOFT)
    private void shimmer(float pX, float pY, float pZ, ChunkBufferBuilderPack pChunkBufferBuilderPack, CallbackInfoReturnable<ChunkRenderDispatcher.RenderChunk.RebuildTask.CompileResults> cir, ChunkRenderDispatcher.RenderChunk.RebuildTask.CompileResults chunkrenderdispatcher$renderchunk$rebuildtask$compileresults, int i, BlockPos blockpos, BlockPos blockpos1, VisGraph visgraph, RenderChunkRegion renderchunkregion, PoseStack posestack, Set<RenderType> set) {
        if (set.contains(ModRenderTypes.shimmerLiquid)) {
            BufferBuilder bufferbuilder1 = pChunkBufferBuilderPack.builder(ModRenderTypes.shimmerLiquid);
            if (!bufferbuilder1.isCurrentBatchEmpty()) {
                bufferbuilder1.setQuadSorting(VertexSorting.byDistance(pX - (float)blockpos.getX(), pY - (float)blockpos.getY(), pZ - (float)blockpos.getZ()));
                chunkrenderdispatcher$renderchunk$rebuildtask$compileresults.transparencyState = bufferbuilder1.getSortState();
            }
        }
    }
}
