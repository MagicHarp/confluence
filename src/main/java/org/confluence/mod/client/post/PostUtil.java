package org.confluence.mod.client.post;


import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import org.confluence.mod.client.post.effect.Bloom;
import org.confluence.mod.client.post.effect.MotionBlur;
import java.util.*;
import java.util.function.Consumer;

import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;

public class PostUtil {

    public static boolean isFirstFrame = true;  // 必须初始化一次
    public static List<PostEffect> effects = new ArrayList<>();

    public static Bloom bloom;
    public static MotionBlur motionBlur;


    // 咖式管线
    public static void postProcess() {
        if(!isFirstFrame) {
            //renderGlow();
            motionBlur.apply();

            backUp();
        }
    }

    // 添加特效
    public static void init() {
        if(isFirstFrame) {
            isFirstFrame = false;
            bloom = new Bloom();
            motionBlur = new MotionBlur();
            effects.add(bloom);
            effects.add(motionBlur);



            effects.forEach(PostEffect::init);
        }
    }

    public static void addEffect(PostEffect effect){
        effects.add(effect);
    }

    public static void backUp(){
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        GlStateManager.glActiveTexture(GL_TEXTURE0);
        GlStateManager._bindTexture(Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
    }

    // 单个特效渲染
    public static void postPass( RenderTarget out, DIYBlitTarget in,DIYShaderInstance shader, Consumer<DIYShaderInstance> samplers,Consumer<UniformsMap> uniforms){
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
    public static void postPass(RenderTarget out,DIYBlitTarget in,  DIYShaderInstance shader, Consumer<DIYShaderInstance> samplers,Consumer<UniformsMap> uniforms,float scale){
        out.bindWrite(true);
        GlStateManager.glActiveTexture(GL_TEXTURE0);
        GlStateManager._bindTexture(out.getColorTextureId());
        in.setBlitShader(shader);
        in.setUniforms(uniforms);
        in.setCreateSampler(samplers);
        in.blitToScreen(
                (int) (Minecraft.getInstance().getWindow().getWidth()*scale),
                (int) (Minecraft.getInstance().getWindow().getHeight()*scale),false
        );
    }




    public static void clear(){
        effects.forEach(PostEffect::clear);
    }
}
