package org.confluence.mod.client.renderer.item.armor;

import org.confluence.mod.Confluence;
import org.confluence.mod.item.armor.SnowArmorItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SnowArmorRenderer extends GeoArmorRenderer<SnowArmorItem> {
    public SnowArmorRenderer() {
        super(new DefaultedItemGeoModel<>(Confluence.asResource("armor/snow_armor")));
    }
}
