package org.confluence.mod.client.renderer.item.armor;

import org.confluence.mod.Confluence;
import org.confluence.mod.item.armor.RaincoatArmorItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class RaincoatArmorRenderer extends GeoArmorRenderer<RaincoatArmorItem> {
    public RaincoatArmorRenderer() {
        super(new DefaultedItemGeoModel<>(Confluence.asResource("armor/raincoat_armor")));
    }
}
