package org.confluence.mod;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.confluence.mod.client.renderer.entity.projectile.*;

import static org.confluence.mod.common.init.ModEntities.*;

@EventBusSubscriber(modid = Confluence.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)

public class ModClient {

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ARROW_PROJECTILE.get(), TerraArrowRenderer::new);

    }
}