package org.confluence.mod.client.renderer.curio;

import org.confluence.mod.item.curio.CurioItems;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class CurioRenderers {
    public static void register() {
        //CuriosRendererRegistry.register(CurioItems.MECHANICAL_LENS.get(), );
        CuriosRendererRegistry.register(CurioItems.SPECTRE_GOGGLES.get(), SpectreGogglesRenderer::new);
        CuriosRendererRegistry.register(CurioItems.TERRASPARK_BOOTS.get(), TerrasparkBootsRenderer::new);
    }
}
