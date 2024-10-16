package org.confluence.mod.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.client.renderer.gui.ArrowInBowOverlay;

import static org.confluence.mod.Confluence.MODID;

@Mod.EventBusSubscriber(modid = MODID,value = Dist.CLIENT)
public class GuiRenderEvent {

    @SubscribeEvent
    public static void RenderGuiEvent(RenderHandEvent event) {
        ArrowInBowOverlay.render(event);





    }
}
