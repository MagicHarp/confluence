package org.confluence.mod.client.post;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.function.Consumer;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL20.glDrawBuffers;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;

public class DIYShaderInstance extends ShaderInstance {
    static final int SAMPLE_CHANNELS = 4;
    public boolean multiOut = false;
    public boolean bind = false;
    public UniformsMap um;
    private RenderTarget multiOutTarget;
    public int textureId;
    private Consumer<UniformsMap> applyUniforms;
    private Consumer<UniformsMap> createUniforms;

    public DIYShaderInstance(ResourceProvider resourceProvider, ResourceLocation shaderLocation, VertexFormat vertexFormat

       , Consumer<UniformsMap> createUniforms) throws IOException {
        super(resourceProvider, shaderLocation, vertexFormat);

        this.createUniforms = createUniforms;
        this.um = new UniformsMap(this.getId());
    }

    public void apply(){
        super.apply();
        if(createUniforms!= null &&um.programId== this.getId() && !um.isReady) createUniforms.accept(um);
        if(applyUniforms!= null && um.programId == this.getId() && um.isReady) applyUniforms.accept(um);

        if(multiOut || bind) {
            multiOut = false;
            //glActiveTexture(GL_TEXTURE0+SAMPLE_CHANNELS);
//            glBindTexture(GL_TEXTURE_2D, textureId);
//            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, Minecraft.getInstance().getMainRenderTarget().width,Minecraft.getInstance().getMainRenderTarget().height,  0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
            glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0+SAMPLE_CHANNELS, GL_TEXTURE_2D, textureId, 0);
            int[] textures = new int[]{GL_COLOR_ATTACHMENT0, GL_COLOR_ATTACHMENT0+SAMPLE_CHANNELS};
            glDrawBuffers(textures);
        }

    }

    public void clear(){
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0+SAMPLE_CHANNELS, GL_TEXTURE_2D, 0, 0);
        applyUniforms = null;
        super.clear();
    }

    public void setUniforms(Consumer<UniformsMap> consumer){
        applyUniforms = consumer;

    }

    public void setMultiOutTarget(RenderTarget target){
        this.multiOutTarget = target;
        this.textureId = multiOutTarget.getColorTextureId();
        multiOut = true;
    }

    public boolean isMultiOut(){
        return multiOutTarget !=null;
    }



/*
    public void bindOutTarget(RenderTarget target){
        this.multiOutTarget = target;
        this.textureId = multiOutTarget.getColorTextureId();
        bind=true;
    }
*/
}
