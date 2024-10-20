package org.confluence.mod.client.post.effect;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import org.confluence.mod.client.post.DIYBlitTarget;
import org.confluence.mod.client.post.PostEffect;
import org.confluence.mod.client.shader.ModRenderTypes;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.sword.LightSaber;
import org.confluence.mod.item.sword.Swords;

import java.util.HashMap;
import java.util.Map;

import static org.confluence.mod.client.post.PostUtil.postPass;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL11C.GL_ONE;

public class Bloom implements PostEffect {

    // 辉光帧缓冲
    public DIYBlitTarget glowTargetOri;
    public DIYBlitTarget glowTargetH;
    public DIYBlitTarget glowTargetV;
    // 施加效果的物品：加入map则采样，selfGlow采样最大亮度，light修正亮度
    public Map<Item,Tuple> itemMap = new HashMap<>();
    public Map<Class<? extends Item>,Tuple> itemClassMap = new HashMap<>();

    public Item mainhandItem;
    public Item offhandItem;
    public boolean shouldRender = false;
    public record Tuple(boolean selfGlow, float light){}
    public Bloom(){

    }
    // 辉光效果
    public void apply() {
        if(!shouldRender) return;

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
                    out,
                    in,
                    ModRenderTypes.Shaders.gaussian_blur,
                    shader-> {
                        shader.setSampler("att", in);
                    },
                    um->{um.setUniform("hor", finalI %2==0?GL_TRUE:GL_FALSE);}
            );
        }

//        glEnable(GL_BLEND);
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

        // 注册辉光效果的物品类型
        itemClassMap.put(LightSaber.class, new Tuple( true, 2.0f));
        itemClassMap.put(BaseCurioItem.class, new Tuple( false, 1.0f));
        itemMap.put(CurioItems.ANKH_SHIELD.get(),new Tuple( true, 1.0f));//覆盖类属性


        // 注册辉光效果的物品 优先级更高
        itemMap.put(Swords.VOLCANO.get(), new Tuple( true, 2.0f));
        itemMap.put(Swords.DEVELOPER_SWORD.get(), new Tuple( false, 1.0f));

    }

    @Override
    public void clear() {
        if(glowTargetOri!=null) glowTargetOri.clear(true);
        if(glowTargetH !=null) glowTargetH.clear(true);
        if(glowTargetV !=null) glowTargetV.clear(true);
        shouldRender = false;
    }
}
