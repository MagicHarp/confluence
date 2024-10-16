package org.confluence.mod.client.shader;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.post.DIYBlitTarget;
import org.confluence.mod.client.post.postUtil;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.sword.Swords;

import static com.mojang.blaze3d.platform.GlStateManager.glActiveTexture;
import static org.confluence.mod.effect.beneficial.helper.SpelunkerHelper.renderLevel;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;

import static org.lwjgl.opengl.GL11.glAlphaFunc;
import static org.lwjgl.opengl.GL30C.*;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class RenderEvents {
    @SubscribeEvent
    public static void renderLevelStage(RenderLevelStageEvent event) {
        renderLevel(event); //洞探
        if(event.getStage()== RenderLevelStageEvent.Stage.AFTER_LEVEL
                && Minecraft.getInstance().player.getMainHandItem().is(Swords.DEVELOPER_SWORD.get())
        ){
//            if( Minecraft.getInstance().player.getMainHandItem().is(Swords.DEVELOPER_SWORD.get())){
                if(ModRenderTypes.Shaders.cth.mt!=null){






                    for(int i=5;i<100;i+=5){
                        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);

                        int finalI = i;
                        ModRenderTypes.Shaders.diy_blit.setUniforms(um->
                                um.setUniform("offs", finalI));

                        ModRenderTypes.Shaders.cth.mt.blitToScreen(
                                Minecraft.getInstance().getWindow().getWidth()/2,
                                Minecraft.getInstance().getWindow().getHeight()/2,
                                false);


                        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
                        glActiveTexture(GL_TEXTURE0);
                        glBindTexture(GL_TEXTURE_2D,  Minecraft.getInstance().getMainRenderTarget().getColorTextureId());

                    }
                    Minecraft.getInstance().getMainRenderTarget().bindWrite(true);


                    ModRenderTypes.Shaders.diy_blit.setUniforms(um->
                            um.setUniform("offs", 10));

                    ModRenderTypes.Shaders.cth.mt.blitToScreen(
                            Minecraft.getInstance().getWindow().getWidth()/2,
                            Minecraft.getInstance().getWindow().getHeight()/2,
                            false);


                    Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
                    glActiveTexture(GL_TEXTURE0);
                    glBindTexture(GL_TEXTURE_2D,  Minecraft.getInstance().getMainRenderTarget().getColorTextureId());

                    /*

                    ModRenderTypes.Shaders.diy_blit.setUniforms(um->um.setUniform("offs",50));

                    ModRenderTypes.Shaders.cth.mt.blitToScreen(
                            Minecraft.getInstance().getWindow().getWidth()/2,
                            Minecraft.getInstance().getWindow().getHeight()/2,
                            false);


                    Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
                    glActiveTexture(GL_TEXTURE0);
                    glBindTexture(GL_TEXTURE_2D,  Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
*/

                }






//            }
        }






    }

    public static void renderLevelStage(RenderGuiOverlayEvent event) {


    }


}
