package org.confluence.mod.client.renderer.item.armor;

import org.confluence.mod.Confluence;
import org.confluence.mod.item.armor.CactusArmorItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class PlatinumArmorRenderer extends GeoArmorRenderer<CactusArmorItem> {
    public PlatinumArmorRenderer() {
        super(new DefaultedItemGeoModel<>(Confluence.asResource("armor/platinum_armor")));
    }
}
