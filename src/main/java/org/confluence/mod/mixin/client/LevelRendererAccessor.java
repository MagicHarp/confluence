package org.confluence.mod.mixin.client;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LevelRenderer.class)
public interface LevelRendererAccessor {
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
