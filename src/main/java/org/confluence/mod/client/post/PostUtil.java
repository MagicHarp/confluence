package org.confluence.mod.client.post;


import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import org.confluence.mod.client.renderer.entity.boss.CthulhuEyeRenderer;
import org.confluence.mod.client.shader.ModRenderTypes;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.mojang.blaze3d.platform.GlStateManager.glActiveTexture;
import static org.confluence.mod.client.renderer.entity.boss.CthulhuEyeRenderer.tempBlurTarget;
import static org.lwjgl.opengl.GL11C.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11C.glBindTexture;
import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;

public class PostUtil {
    public static DIYBlitTarget glowTarget;


    public static DIYBlitTarget cthBlurTarget;
    public static List<blurTuple> blurList = new ArrayList<>();
    public record blurTuple(DIYBlitTarget fbo, float distance, Vector2f dir){}




    public static void renderGlow() {
    }
    public static void renderCthBlur(){
        // 触发条件
        if(ModRenderTypes.Shaders.cthSampler.isMultiOut()){
            // 迭代模糊
//            for(int i=1;i<=5;i++) {
                postPass(tempBlurTarget,                                            //输入FBO
                        tempBlurTarget,                                             //输出FBO
                        ModRenderTypes.Shaders.conv,                                //使用shader
                        shader-> shader.setSampler("att", tempBlurTarget),  //采样
                        um-> {
                            um.setUniform("dir", new Vector2f(10,10));
                            um.setUniform("dist", 10.0f);          //uniform
                        }
                );
//            }

            // 混合方式
            postPass(tempBlurTarget,                                                                    //输入FBO
                    Minecraft.getInstance().getMainRenderTarget(),                                      //输出FBO
                    ModRenderTypes.Shaders.diy_blit,                                                    //shader定义实现的混合方式
                    shader-> {
                        shader.setSampler("ori",Minecraft.getInstance().getMainRenderTarget()); //采样主屏幕
                        shader.setSampler("att", tempBlurTarget);                               //采样当前帧缓冲
                    },
                    null
                    //2                                                                                   //视图缩放倍数
            );
        }
    }

    public static void postProcess() {
        renderGlow();
        renderCthBlur();

        backUp();
    }

    public static void backUp(){
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D,  Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
    }

    public static boolean isFirstFrame = true;
    public static void init() {
        int width = Minecraft.getInstance().getWindow().getWidth();
        int height = Minecraft.getInstance().getWindow().getHeight();
        glowTarget = new DIYBlitTarget(width, height,false,true,ModRenderTypes.Shaders.diy_blit,shader->{
        });
        cthBlurTarget = new DIYBlitTarget(width, height,false,true,ModRenderTypes.Shaders.conv,shader->{
//            shader.setSampler("ori",  Minecraft.getInstance().getMainRenderTarget());
//            shader.setSampler("att", cthBlurTarget);
        });
        cthBlurTarget.setClearColor(0,0,0,0);

    }

    public static void postPass(DIYBlitTarget in, RenderTarget out, DIYShaderInstance shader, Consumer<DIYShaderInstance> samplers,Consumer<UniformsMap> uniforms){
        out.bindWrite(true);
        GlStateManager.glActiveTexture(GL_TEXTURE0);
        GlStateManager._bindTexture(out.getColorTextureId());
        in.setBlitShader(shader);
        in.setUniforms(uniforms);
        in.setCreateSampler(samplers);
        in.blitToScreen(
                Minecraft.getInstance().getWindow().getWidth(),
                Minecraft.getInstance().getWindow().getHeight(),false
        );
    }

    public static void postPass(DIYBlitTarget in, RenderTarget out, DIYShaderInstance shader, Consumer<DIYShaderInstance> samplers,Consumer<UniformsMap> uniforms,int scale){
        out.bindWrite(true);
        GlStateManager.glActiveTexture(GL_TEXTURE0);
        GlStateManager._bindTexture(out.getColorTextureId());
        in.setBlitShader(shader);
        in.setUniforms(uniforms);
        in.setCreateSampler(samplers);
        in.blitToScreen(
                Minecraft.getInstance().getWindow().getWidth()/scale,
                Minecraft.getInstance().getWindow().getHeight()/scale,false
        );
    }

    public static void clear(){
        if(glowTarget!=null) glowTarget.clear(true);
        if(cthBlurTarget!=null) cthBlurTarget.clear(true);
        if(CthulhuEyeRenderer.tempBlurTarget!=null) CthulhuEyeRenderer.tempBlurTarget.clear(true);
        blurList.clear();
    }

}
