package org.confluence.mod.client.post.effect;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.client.post.DIYBlitTarget;
import org.confluence.mod.client.post.PostEffect;
import org.confluence.mod.client.shader.ModRenderTypes;
import org.joml.Vector2f;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.confluence.mod.client.post.PostUtil.postPass;

public class MotionBlur implements PostEffect {

    // 运动模糊帧缓冲
    public Map<Object,blurTuple> blurList;
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


    @Override
    public void init() {
        blurList = new LinkedHashMap<>();
    }

    @Override
    public void clear() {

        blurList.forEach((k,v)->{
            v.dirty = false;
            v.fbo.clear(true);
            if(k instanceof LivingEntity && !((LivingEntity) k).isAlive()) blurList.remove(v);

        });
    }

    @Override
    public void apply() {
        // 触发条件
        blurList.forEach((k,v)->{
            if(v.dirty){

                // 迭代模糊
//            for(int i=1;i<=5;i++) {
                postPass(v.fbo,                                                             //输出FBO
                        v.fbo,                                                              //输入FBO
                        ModRenderTypes.Shaders.motion_blur,                                        //使用shader
                        shader-> shader.setSampler("att", v.fbo),                   //采样
                        um-> {
                            um.setUniform("dir", v.dir);
                            um.setUniform("dist", v.distance);               //uniform
                        }
                );
//            }

                // 混合方式
                postPass(
                        Minecraft.getInstance().getMainRenderTarget(),                                      //输出FBO
                        v.fbo,                                                                              //输入FBO
                        ModRenderTypes.Shaders.diy_blit_mix_add,                                            //shader定义实现的混合方式
                        shader-> {
                            shader.setSampler("ori",Minecraft.getInstance().getMainRenderTarget()); //采样主屏幕
                            shader.setSampler("att", v.fbo);                                        //采样当前帧缓冲
                        },
                        null
                        //视图缩放倍数
                );
            }
        });
    }
}
