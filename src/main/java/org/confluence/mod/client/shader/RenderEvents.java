package org.confluence.mod.client.shader;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.post.PostUtil;


import static org.confluence.mod.effect.beneficial.helper.SpelunkerHelper.renderLevel;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class RenderEvents {
    @SubscribeEvent
    public static void renderLevelStage(RenderLevelStageEvent event) {
        renderLevel(event); //洞探

        if(event.getStage()== RenderLevelStageEvent.Stage.AFTER_LEVEL
//                && Minecraft.getInstance().player.getMainHandItem().is(Swords.DEVELOPER_SWORD.get())
        ){
                PostUtil.postProcess();
            /*

                if(ModRenderTypes.Shaders.cthSampler.isMultiOut()){
                    for(int i=1;i<=5;i++) {
                        int finalI = i;
                        tempBlurTarget.bindWrite(true);
                        GlStateManager.glActiveTexture(GL_TEXTURE0);
                        GlStateManager._bindTexture(tempBlurTarget.getColorTextureId());
                        //GlStateManager._bindTexture(tempBlurTarget.getColorTextureId());
                        tempBlurTarget.setBlitShader(ModRenderTypes.Shaders.conv);
                        tempBlurTarget.setUniforms(um -> um.setUniform("offs", 10));
                        tempBlurTarget.setCreateSampler(um-> {
//                            um.setSampler("ori", cthBlurTarget);
                            um.setSampler("att", tempBlurTarget);
                        });
                        tempBlurTarget.blitToScreen(Minecraft.getInstance().getWindow().getWidth(),
                                Minecraft.getInstance().getWindow().getHeight());
                        //tempBlurTarget.unbindWrite();
                    }


                    Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
                    GlStateManager.glActiveTexture(GL_TEXTURE0);
                    GlStateManager._bindTexture(Minecraft.getInstance().getMainRenderTarget().getColorTextureId());

//                    ModRenderTypes.Shaders.diy_blit.setUniforms(um->
//                            um.setUniform("offs", finalI));
                        GlStateManager._blendFunc(GlStateManager.SourceFactor.ONE_MINUS_SRC_ALPHA.value,  GlStateManager.DestFactor.ONE_MINUS_DST_ALPHA.value);
                    tempBlurTarget.setBlitShader(ModRenderTypes.Shaders.diy_blit);
                    tempBlurTarget.setCreateSampler(um->{
                        um.setSampler("ori",Minecraft.getInstance().getMainRenderTarget());
                        um.setSampler("att", tempBlurTarget);
                    });
                    tempBlurTarget.blitToScreen(
                            Minecraft.getInstance().getWindow().getWidth()/2,
                            Minecraft.getInstance().getWindow().getHeight()/2,false
                            );
                    Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
                    glActiveTexture(GL_TEXTURE0);
                    glBindTexture(GL_TEXTURE_2D,  Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
*/

                    /*
                    Minecraft.getInstance().getMainRenderTarget().bindWrite(true);


                    ModRenderTypes.Shaders.diy_blit.setUniforms(um->
                            um.setUniform("offs", 10));

                    ModRenderTypes.Shaders.cthSampler.multiOutTarget.blitToScreen(
                            Minecraft.getInstance().getWindow().getWidth()/2,
                            Minecraft.getInstance().getWindow().getHeight()/2,
                            false);


                    Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
                    glActiveTexture(GL_TEXTURE0);
                    glBindTexture(GL_TEXTURE_2D,  Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
*/
                    /*

                    ModRenderTypes.Shaders.diy_blit.setUniforms(um->um.setUniform("offs",50));

                    ModRenderTypes.Shaders.cthSampler.multiOutTarget.blitToScreen(
                            Minecraft.getInstance().getWindow().getWidth()/2,
                            Minecraft.getInstance().getWindow().getHeight()/2,
                            false);


                    Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
                    glActiveTexture(GL_TEXTURE0);
                    glBindTexture(GL_TEXTURE_2D,  Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
*/

//                }


        }
    }


    @SubscribeEvent
    public static void onRenderTickEnd(TickEvent.RenderTickEvent event){

        if(event.phase == TickEvent.Phase.END && event.type == TickEvent.Type.RENDER){
            PostUtil.clear();

        }
    }



}
