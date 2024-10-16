package org.confluence.mod.client.post;

import com.mojang.blaze3d.pipeline.MainTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import org.confluence.mod.client.shader.ModRenderTypes;
import org.joml.Matrix4f;

import static com.mojang.blaze3d.platform.GlStateManager.glActiveTexture;
import static org.lwjgl.opengl.GL13C.*;
import static org.lwjgl.opengl.GL30C.*;

public class DIYBlitTarget extends TextureTarget {
    public DIYBlitTarget(int pWidth, int pHeight, boolean pUseDepth, boolean pClearError) {
        super(pWidth, pHeight, pUseDepth, pClearError);

        if(blitShader == null) blitShader = ModRenderTypes.Shaders.diy_blit;
    }

    private static DIYShaderInstance blitShader;

    public void blitToScreen(int pWidth, int pHeight, boolean pDisableBlend) {
        RenderSystem.assertOnGameThreadOrInit();
        if (!RenderSystem.isInInitPhase()) {
            RenderSystem.recordRenderCall(() -> {
                this._blitToScreen(pWidth, pHeight, pDisableBlend);
            });
        } else {
            this._blitToScreen(pWidth, pHeight, pDisableBlend);
        }

    }
/*
    private void _blitToScreen(int pWidth, int pHeight, boolean pDisableBlend) {
        RenderSystem.assertOnRenderThread();
        GlStateManager._colorMask(true, true, true, false);
        GlStateManager._disableDepthTest();
        GlStateManager._depthMask(false);
        GlStateManager._viewport(0, 0, pWidth, pHeight);
        if (pDisableBlend) {
            GlStateManager._disableBlend();
        }

        Minecraft minecraft = Minecraft.getInstance();
        DIYShaderInstance shaderinstance = ModRenderTypes.Shaders.diy_blit;
                //minecraft.gameRenderer.blitShader;
        shaderinstance.setSampler("DiffuseSampler", shaderinstance.textureId);
        shaderinstance.setSampler("Sampler1", minecraft.getMainRenderTarget().getColorTextureId());
        Matrix4f matrix4f = (new Matrix4f()).setOrtho(0.0F, (float)pWidth, (float)pHeight, 0.0F, 1000.0F, 3000.0F);
        RenderSystem.setProjectionMatrix(matrix4f, VertexSorting.ORTHOGRAPHIC_Z);
        if (shaderinstance.MODEL_VIEW_MATRIX != null) {
            shaderinstance.MODEL_VIEW_MATRIX.set((new Matrix4f()).translation(0.0F, 0.0F, -2000.0F));
        }

        if (shaderinstance.PROJECTION_MATRIX != null) {
            shaderinstance.PROJECTION_MATRIX.set(matrix4f);
        }

        shaderinstance.apply();
        float f = (float)pWidth;
        float f1 = (float)pHeight;
        float f2 = (float)this.viewWidth / (float)this.width;
        float f3 = (float)this.viewHeight / (float)this.height;
        Tesselator tesselator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.vertex(0.0D, (double)f1, 0.0D).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
        bufferbuilder.vertex((double)f, (double)f1, 0.0D).uv(f2, 0.0F).color(255, 255, 255, 255).endVertex();
        bufferbuilder.vertex((double)f, 0.0D, 0.0D).uv(f2, f3).color(255, 255, 255, 255).endVertex();
        bufferbuilder.vertex(0.0D, 0.0D, 0.0D).uv(0.0F, f3).color(255, 255, 255, 255).endVertex();
        BufferUploader.draw(bufferbuilder.end());
        shaderinstance.clear();
        GlStateManager._depthMask(true);
        GlStateManager._colorMask(true, true, true, true);
    }
*/


    private void _blitToScreen(int pWidth, int pHeight, boolean pDisableBlend) {
        RenderSystem.assertOnRenderThread();
        GlStateManager._colorMask(true, true, true, false);
        GlStateManager._disableDepthTest();
        GlStateManager._depthMask(false);
        GlStateManager._viewport(0, 0, pWidth, pHeight);
        if (pDisableBlend) {
            GlStateManager._disableBlend();
        }


        if(blitShader == null) blitShader = ModRenderTypes.Shaders.diy_blit;

        DIYShaderInstance shaderinstance = blitShader;
//        blitShader.setSampler("DiffuseSampler", Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
//        blitShader.setSampler("Sampler1", this.colorTextureId);
        Matrix4f matrix4f = (new Matrix4f()).setOrtho(0.0F, (float)pWidth, (float)pHeight, 0.0F, 1000.0F, 3000.0F);
        RenderSystem.setProjectionMatrix(matrix4f, VertexSorting.ORTHOGRAPHIC_Z);



        shaderinstance.setSampler("ori",  Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
        shaderinstance.setSampler("att",  this.colorTextureId);




        if (shaderinstance.MODEL_VIEW_MATRIX != null) {
            shaderinstance.MODEL_VIEW_MATRIX.set((new Matrix4f()).translation(0.0F, 0.0F, -2000.0F));
        }

        if (shaderinstance.PROJECTION_MATRIX != null) {
            shaderinstance.PROJECTION_MATRIX.set(matrix4f);
        }


        shaderinstance.apply();
        float f = (float)pWidth;
        float f1 = (float)pHeight;
        float f2 = (float)this.viewWidth / (float)this.width;
        float f3 = (float)this.viewHeight / (float)this.height;
        Tesselator tesselator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.vertex(0.0D, f1, 0.0D).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
        bufferbuilder.vertex(f, f1, 0.0D).uv(f2, 0.0F).color(255, 255, 255, 255).endVertex();
        bufferbuilder.vertex(f, 0.0D, 0.0D).uv(f2, f3).color(255, 255, 255, 255).endVertex();
        bufferbuilder.vertex(0.0D, 0.0D, 0.0D).uv(0.0F, f3).color(255, 255, 255, 255).endVertex();
        BufferUploader.draw(bufferbuilder.end());

//        glActiveTexture(GL_TEXTURE5);
//        glBindTexture(GL_TEXTURE_2D, 0);

        shaderinstance.clear();
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, 0);

        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, 0);

        GlStateManager._depthMask(true);
        GlStateManager._colorMask(true, true, true, true);
    }


}
