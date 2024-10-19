package org.confluence.mod.client.post;

import com.mojang.blaze3d.pipeline.MainTarget;
import com.mojang.blaze3d.pipeline.RenderTarget;
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
import org.lwjgl.opengl.GL11;

import java.util.function.Consumer;

import static com.mojang.blaze3d.platform.GlStateManager.glActiveTexture;
import static org.lwjgl.opengl.GL13C.*;
import static org.lwjgl.opengl.GL30C.*;

public class DIYBlitTarget extends TextureTarget {
    public DIYBlitTarget(int pWidth, int pHeight, boolean pUseDepth, boolean pClearError, DIYShaderInstance blitShader) {
        super(pWidth, pHeight, pUseDepth, pClearError);
        this.blitShader = blitShader;
    }
    private boolean autoClear = true;
    private Consumer<DIYShaderInstance> createSampler;
    private DIYShaderInstance blitShader;
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

    private void _blitToScreen(int pWidth, int pHeight, boolean pDisableBlend) {
        RenderSystem.assertOnRenderThread();
        GlStateManager._colorMask(true, true, true, false);
        GlStateManager._disableDepthTest();
        GlStateManager._depthMask(false);
        GlStateManager._viewport(0, 0, pWidth, pHeight);
        if (pDisableBlend) {
            GlStateManager._disableBlend();
        }

        Matrix4f matrix4f = (new Matrix4f()).setOrtho(0.0F, (float)pWidth, (float)pHeight, 0.0F, 1000.0F, 3000.0F);
        RenderSystem.setProjectionMatrix(matrix4f, VertexSorting.ORTHOGRAPHIC_Z);
        if(createSampler!= null) createSampler.accept(blitShader);
//        blitShader.setSampler("ori",  Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
//        blitShader.setSampler("att",  this.colorTextureId);

        if (blitShader.MODEL_VIEW_MATRIX != null) {
            blitShader.MODEL_VIEW_MATRIX.set((new Matrix4f()).translation(0.0F, 0.0F, -2000.0F));
        }

        if (blitShader.PROJECTION_MATRIX != null) {
            blitShader.PROJECTION_MATRIX.set(matrix4f);
        }


        blitShader.apply();
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

        blitShader.clear();
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, 0);

        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, 0);


        GlStateManager._depthMask(true);

        GlStateManager._colorMask(true, true, true, true);
    }


    public void setBlitShader(DIYShaderInstance blitShader) {
        this.blitShader = blitShader;
    }
    public DIYShaderInstance getBlitShader() {
        return this.blitShader;
    }
    public void setUniforms(Consumer<UniformsMap> consumer) {
        this.blitShader.setUniforms(consumer);
    }
    public void setCreateSampler(Consumer<DIYShaderInstance> blitShader) {
        this.createSampler = blitShader;
    }
    public void setAutoClear(boolean autoClear) {
        this.autoClear = autoClear;
    }
    public void clear(boolean error){
        super.clear(error);
        if( autoClear && (this.viewHeight!=Minecraft.getInstance().getWindow().getHeight() || this.viewWidth!=Minecraft.getInstance().getWindow().getWidth())){
            this.resize(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight(),error);
        }
    }
}
