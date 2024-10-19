package org.confluence.mod.event;

import net.minecraft.world.InteractionHand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.client.post.PostUtil;
import org.confluence.mod.client.renderer.gui.ArrowInBowOverlay;
import org.confluence.mod.client.shader.ModRenderTypes;

import static org.confluence.mod.Confluence.MODID;
import static org.confluence.mod.client.post.PostUtil.*;

@Mod.EventBusSubscriber(modid = MODID,value = Dist.CLIENT)
public class GuiRenderEvent {

    @SubscribeEvent
    public static void renderHandEvent(RenderHandEvent event) {
        ArrowInBowOverlay.render(event);

        if(event.getHand()== InteractionHand.OFF_HAND){
            PostUtil.bloom.glowTargetOri.bindWrite(true);
            ModRenderTypes.Shaders.positionColorSampler.setMultiOutTarget(PostUtil.bloom.glowTargetH);
            ModRenderTypes.Shaders.positionColorSampler.apply();

        }

    }
    @SubscribeEvent
    public static void renderGuiEvent(RenderGuiEvent event) {

    }

}
