package org.confluence.mod.client.shader;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.mixin.client.accessor.LevelRendererAccessor;
import org.confluence.mod.mixin.client.accessor.RenderChunkInfoAccessor;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class RenderEvents {
    //@SubscribeEvent
    public static void renderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) {
            RenderType pRenderType = ModRenderTypes.shimmerLiquid;
            LevelRenderer levelRenderer = event.getLevelRenderer();
            Minecraft minecraft = Minecraft.getInstance();
            PoseStack pPoseStack = event.getPoseStack();
            Matrix4f pProjectionMatrix = event.getProjectionMatrix();
            Camera camera = event.getCamera();
            double pCamX = camera.getPosition().x();
            double pCamY = camera.getPosition().y();
            double pCamZ = camera.getPosition().z();

            RenderSystem.assertOnRenderThread();
            pRenderType.setupRenderState();
            LevelRendererAccessor accessor = (LevelRendererAccessor) levelRenderer;

            minecraft.getProfiler().push("translucent_sort");
            double d0 = pCamX - accessor.getXTransparentOld();
            double d1 = pCamY - accessor.getYTransparentOld();
            double d2 = pCamZ - accessor.getZTransparentOld();
            if (d0 * d0 + d1 * d1 + d2 * d2 > 1.0D) {
                int j = SectionPos.posToSectionCoord(pCamX);
                int k = SectionPos.posToSectionCoord(pCamY);
                int l = SectionPos.posToSectionCoord(pCamZ);
                boolean flag = j != SectionPos.posToSectionCoord(accessor.getXTransparentOld()) || l != SectionPos.posToSectionCoord(accessor.getZTransparentOld()) || k != SectionPos.posToSectionCoord(accessor.getYTransparentOld());
                accessor.setXTransparentOld(pCamX);
                accessor.setYTransparentOld(pCamY);
                accessor.setZTransparentOld(pCamZ);
                int i1 = 0;

                for(LevelRenderer.RenderChunkInfo levelrenderer$renderchunkinfo : accessor.getRenderChunksInFrustum()) {
                    if (i1 < 15 && (flag || levelrenderer$renderchunkinfo.isAxisAlignedWith(j, k, l)) && ((RenderChunkInfoAccessor) levelrenderer$renderchunkinfo).getChunk().resortTransparency(pRenderType, accessor.getChunkRenderDispatcher())) {
                        ++i1;
                    }
                }
            }

            minecraft.getProfiler().pop();

            minecraft.getProfiler().push("filterempty");
            minecraft.getProfiler().popPush(() -> "render_" + pRenderType);
            ObjectArrayList<LevelRenderer.RenderChunkInfo> renderChunksInFrustum = accessor.getRenderChunksInFrustum();
            ObjectListIterator<LevelRenderer.RenderChunkInfo> objectlistiterator = renderChunksInFrustum.listIterator(renderChunksInFrustum.size());
            ShaderInstance shaderinstance = RenderSystem.getShader();

            for (int i = 0; i < 12; ++i) {
                int j1 = RenderSystem.getShaderTexture(i);
                shaderinstance.setSampler("Sampler" + i, j1);
            }

            if (shaderinstance.MODEL_VIEW_MATRIX != null) {
                shaderinstance.MODEL_VIEW_MATRIX.set(pPoseStack.last().pose());
            }

            if (shaderinstance.PROJECTION_MATRIX != null) {
                shaderinstance.PROJECTION_MATRIX.set(pProjectionMatrix);
            }

            if (shaderinstance.COLOR_MODULATOR != null) {
                shaderinstance.COLOR_MODULATOR.set(RenderSystem.getShaderColor());
            }

            if (shaderinstance.GLINT_ALPHA != null) {
                shaderinstance.GLINT_ALPHA.set(RenderSystem.getShaderGlintAlpha());
            }

            if (shaderinstance.FOG_START != null) {
                shaderinstance.FOG_START.set(RenderSystem.getShaderFogStart());
            }

            if (shaderinstance.FOG_END != null) {
                shaderinstance.FOG_END.set(RenderSystem.getShaderFogEnd());
            }

            if (shaderinstance.FOG_COLOR != null) {
                shaderinstance.FOG_COLOR.set(RenderSystem.getShaderFogColor());
            }

            if (shaderinstance.FOG_SHAPE != null) {
                shaderinstance.FOG_SHAPE.set(RenderSystem.getShaderFogShape().getIndex());
            }

            if (shaderinstance.TEXTURE_MATRIX != null) {
                shaderinstance.TEXTURE_MATRIX.set(RenderSystem.getTextureMatrix());
            }

            if (shaderinstance.GAME_TIME != null) {
                shaderinstance.GAME_TIME.set(RenderSystem.getShaderGameTime());
            }

            RenderSystem.setupShaderLights(shaderinstance);
            shaderinstance.apply();
            Uniform uniform = shaderinstance.CHUNK_OFFSET;

            while (objectlistiterator.hasPrevious()) {
                LevelRenderer.RenderChunkInfo levelrenderer$renderchunkinfo1 = objectlistiterator.previous();
                ChunkRenderDispatcher.RenderChunk chunkrenderdispatcher$renderchunk = ((RenderChunkInfoAccessor) levelrenderer$renderchunkinfo1).getChunk();
                if (!chunkrenderdispatcher$renderchunk.getCompiledChunk().isEmpty(pRenderType)) {
                    VertexBuffer vertexbuffer = chunkrenderdispatcher$renderchunk.getBuffer(pRenderType);
                    BlockPos blockpos = chunkrenderdispatcher$renderchunk.getOrigin();
                    if (uniform != null) {
                        uniform.set((float) ((double) blockpos.getX() - pCamX), (float) ((double) blockpos.getY() - pCamY), (float) ((double) blockpos.getZ() - pCamZ));
                        uniform.upload();
                    }

                    vertexbuffer.bind();
                    vertexbuffer.draw();
                }
            }

            if (uniform != null) {
                uniform.set(0.0F, 0.0F, 0.0F);
            }

            shaderinstance.clear();
            VertexBuffer.unbind();
            minecraft.getProfiler().pop();
            pRenderType.clearRenderState();
        }
    }
}
