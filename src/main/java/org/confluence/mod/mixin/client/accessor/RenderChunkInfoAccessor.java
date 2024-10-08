package org.confluence.mod.mixin.client.accessor;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LevelRenderer.RenderChunkInfo.class)
public interface RenderChunkInfoAccessor {
    @Accessor
    ChunkRenderDispatcher.RenderChunk getChunk();
}
