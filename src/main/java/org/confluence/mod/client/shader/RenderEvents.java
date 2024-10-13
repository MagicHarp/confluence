package org.confluence.mod.client.shader;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;

import static org.confluence.mod.effect.beneficial.helper.SpelunkerHelper.renderLevel;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class RenderEvents {
    @SubscribeEvent
    public static void renderLevelStage(RenderLevelStageEvent event) {
        renderLevel(event); //洞探
    }
}
