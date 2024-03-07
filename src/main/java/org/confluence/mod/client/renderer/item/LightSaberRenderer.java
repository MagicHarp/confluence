package org.confluence.mod.client.renderer.item;

import org.confluence.mod.client.model.item.LightSaberModel;
import org.confluence.mod.item.sword.LightSaber;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class LightSaberRenderer extends GeoItemRenderer<LightSaber> {
    public LightSaberRenderer(String color) {
        super(new LightSaberModel(color));
    }
}
