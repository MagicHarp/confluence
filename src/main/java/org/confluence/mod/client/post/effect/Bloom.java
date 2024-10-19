package org.confluence.mod.client.post.effect;

import net.minecraft.client.Minecraft;
import org.confluence.mod.client.post.DIYBlitTarget;
import org.confluence.mod.client.post.PostEffect;
import org.confluence.mod.client.shader.ModRenderTypes;

import static org.confluence.mod.client.post.PostUtil.postPass;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL11C.GL_ONE;

public class Bloom implements PostEffect {

    // 辉光帧缓冲
    public DIYBlitTarget glowTargetOri;
    public DIYBlitTarget glowTargetH;
    public DIYBlitTarget glowTargetV;
    public Bloom(){

    }
    // 辉光效果
    public void apply() {

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        postPass(
                Minecraft.getInstance().getMainRenderTarget(),
                glowTargetOri,
                ModRenderTypes.Shaders.diy_blit,
                shader-> {
                    //shader.setSampler("ori", Minecraft.getInstance().getMainRenderTarget());
                    shader.setSampler("att", glowTargetOri);
                },
                null
        );
        glDisable(GL_BLEND);
        for(int i=0;i<20;i++){
            int finalI = i;
            var out =i%2==0? glowTargetV: glowTargetH;
            var in =i%2==0? glowTargetH: glowTargetV;
            postPass(
                    out,                                      //输出FBO
                    in,                                                                             //输入FBO
                    ModRenderTypes.Shaders.gaussian_blur,                                                    //shader定义实现的混合方式
                    shader-> {
                        shader.setSampler("att", in);                                        //采样当前帧缓冲
                    },
                    um->{um.setUniform("hor", finalI %2==0?GL_TRUE:GL_FALSE);}
            );
        }

//        glEnable(GL_BLEND);
//        glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO);
/*
//        glBlendFunc(GL_ONE, GL_ONE);
        postPass(
                glowTargetH,
                glowTargetOri,
                ModRenderTypes.Shaders.diy_blit_gamma,
                shader-> {
                    shader.setSampler("ori", glowTargetH);
                    shader.setSampler("att", glowTargetOri);
                },
                null
        );
*/

//        glEnable(GL_BLEND);
//
        //glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO);
        //glBlendFunc(GL_SRC_COLOR, GL_ONE_MINUS_SRC_COLOR);
        glEnable(GL_BLEND);
        //glBlendFunc(GL_SRC_COLOR, GL_ONE_MINUS_SRC_COLOR);
        glBlendFunc(GL_ONE, GL_ONE);
        postPass(
                Minecraft.getInstance().getMainRenderTarget(),
                glowTargetH,
                ModRenderTypes.Shaders.diy_blit,
                shader-> {
                    //shader.setSampler("ori", Minecraft.getInstance().getMainRenderTarget());
                    shader.setSampler("att", glowTargetH);
                },
                null
        );

    }

    @Override
    public void init() {
        int width = Minecraft.getInstance().getWindow().getWidth();
        int height = Minecraft.getInstance().getWindow().getHeight();

        glowTargetOri = new DIYBlitTarget(width, height, false, true, ModRenderTypes.Shaders.diy_blit);
        glowTargetOri.setClearColor(0, 0, 0, 0);

        glowTargetH = new DIYBlitTarget(width, height, false, true, ModRenderTypes.Shaders.diy_blit);
        glowTargetH.setClearColor(0, 0, 0, 0);

        glowTargetV = new DIYBlitTarget(width, height, false, true, ModRenderTypes.Shaders.diy_blit);
        glowTargetV.setClearColor(0, 0, 0, 0);
    }

    @Override
    public void clear() {
        if(glowTargetOri!=null) glowTargetOri.clear(true);
        if(glowTargetH !=null) glowTargetH.clear(true);
        if(glowTargetV !=null) glowTargetV.clear(true);

    }

}
