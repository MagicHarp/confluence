package org.confluence.mod.client.post;


import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.client.renderer.entity.boss.CthulhuEyeRenderer;
import org.confluence.mod.client.shader.ModRenderTypes;
import org.confluence.mod.item.sword.Swords;
import org.joml.Vector2f;

import java.util.*;
import java.util.function.Consumer;

import static com.mojang.blaze3d.platform.GlStateManager.glActiveTexture;
//import static org.confluence.mod.client.renderer.entity.boss.CthulhuEyeRenderer.tempBlurTarget;
import static org.lwjgl.opengl.GL11C.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11C.glBindTexture;
import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;

public class PostUtil {
    // 辉光帧缓冲
    public static DIYBlitTarget glowTarget;
    // 辉光效果
    public static void renderGlow() {
//        Minecraft.getInstance().player.getMainHandItem().is(Swords.DEVELOPER_SWORD.get())

    }


    // 运动模糊帧缓冲
    public static DIYBlitTarget cthBlurTarget;
    public static Map<Object,blurTuple> blurList = new LinkedHashMap<>();
    public static class blurTuple{
        public DIYBlitTarget fbo;
        public float distance;
        public Vector2f dir;
        public boolean dirty;
        public blurTuple(DIYBlitTarget fbo, float distance, Vector2f dir, boolean dirty){
            this.dir = dir;
            this.distance = distance;
            this.fbo = fbo;
            this.dirty = dirty;
        }
    }

    // 运动模糊效果
    public static void renderCthBlur(){
        // 触发条件
        blurList.forEach((k,v)->{
            if(v.dirty){

                // 迭代模糊
//            for(int i=1;i<=5;i++) {
                postPass(v.fbo,                                                             //输入FBO
                        v.fbo,                                                              //输出FBO
                        ModRenderTypes.Shaders.conv,                                        //使用shader
                        shader-> shader.setSampler("att", v.fbo),                   //采样
                        um-> {
                            um.setUniform("dir", v.dir);
                            um.setUniform("dist", v.distance);               //uniform
                        }
                );
//            }

                // 混合方式
                postPass(v.fbo,                                                                             //输入FBO
                        Minecraft.getInstance().getMainRenderTarget(),                                      //输出FBO
                        ModRenderTypes.Shaders.diy_blit,                                                    //shader定义实现的混合方式
                        shader-> {
                            shader.setSampler("ori",Minecraft.getInstance().getMainRenderTarget()); //采样主屏幕
                            shader.setSampler("att", v.fbo);                                        //采样当前帧缓冲
                        },
                        null
                        //2                                                                                 //视图缩放倍数
                );
            }
        });
    }

    // 咖式管线
    public static void postProcess() {
        if(!isFirstFrame) {
            renderGlow();
            renderCthBlur();

            backUp();
        }
    }

    public static void backUp(){
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D,  Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
    }

    public static boolean isFirstFrame = true;  // 必须初始化一次
    public static void init() {
        if(isFirstFrame) {
            isFirstFrame = false;
            int width = Minecraft.getInstance().getWindow().getWidth();
            int height = Minecraft.getInstance().getWindow().getHeight();
            glowTarget = new DIYBlitTarget(width, height, false, true, ModRenderTypes.Shaders.diy_blit);
            cthBlurTarget = new DIYBlitTarget(width, height, false, true, ModRenderTypes.Shaders.conv);
            cthBlurTarget.setClearColor(0, 0, 0, 0);
        }
    }

    // 单个特效渲染
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

    // 重载用于缩放
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

        blurList.forEach((k,v)->{
            v.dirty = false;
            v.fbo.clear(true);
            if(k instanceof LivingEntity && !((LivingEntity) k).isAlive()) blurList.remove(v);

        });
    }

}
