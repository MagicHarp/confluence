package org.confluence.mod.mixin.accessor;

import com.mojang.blaze3d.vertex.BufferBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.client.renderer.chunk.ChunkRenderDispatcher$RenderChunk$RebuildTask$CompileResults")
public interface CompileResultsAccessor {
    @Accessor
    void setTransparencyState(BufferBuilder.SortState state);
}
