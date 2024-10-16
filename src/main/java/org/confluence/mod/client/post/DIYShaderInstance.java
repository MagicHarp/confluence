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
    public boolean out = false;
    public UniformsMap um;
    public RenderTarget mt;
    int textureId;
    private Consumer<UniformsMap> applyUniforms;
    private Consumer<UniformsMap> createUniforms;

    public DIYShaderInstance(ResourceProvider resourceProvider, ResourceLocation shaderLocation, VertexFormat vertexFormat

       , Consumer<UniformsMap> createUniforms) throws IOException {
        super(resourceProvider, shaderLocation, vertexFormat);
        mt =new DIYBlitTarget(Minecraft.getInstance().getMainRenderTarget().width, Minecraft.getInstance().getMainRenderTarget().height,false,true);
        textureId= mt.getColorTextureId();
        this.createUniforms = createUniforms;
    }

    public void apply(){
        mt.setClearColor(0,0,0,0);
        mt.clear(true);
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        super.apply();


        if(um == null ){
            um = new UniformsMap(this.getId());
            if(createUniforms!= null &&um.programId== this.getId()
                    //&& um.programId== this.getId()
            ) createUniforms.accept(um);
            //um.createUniform("samcolor");
        }
        if(applyUniforms!= null && um.programId == this.getId())
            applyUniforms.accept(um);
        //um.setUniform("samcolor", new Vector4f(0,1,0,1));

        if(!out)return;
        out = false;

        glBindTexture(GL_TEXTURE_2D, textureId);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, Minecraft.getInstance().getMainRenderTarget().width,Minecraft.getInstance().getMainRenderTarget().height,  0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0+SAMPLE_CHANNELS, GL_TEXTURE_2D, textureId, 0);
        int[] textures = new int[]{GL_COLOR_ATTACHMENT0, GL_COLOR_ATTACHMENT0+SAMPLE_CHANNELS};
        glDrawBuffers(textures);
    }

    public void clear(){
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0+SAMPLE_CHANNELS, GL_TEXTURE_2D, 0, 0);
        applyUniforms = null;
        super.clear();
    }

    public void setUniforms(Consumer<UniformsMap> consumer){
        applyUniforms = consumer;
    }



}
