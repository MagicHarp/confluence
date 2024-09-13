package org.confluence.mod.client.renderer.item.armor;

import org.confluence.mod.Confluence;
import org.confluence.mod.item.armor.CactusArmorItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SilverArmorRenderer extends GeoArmorRenderer<CactusArmorItem> {
    public SilverArmorRenderer() {
        super(new DefaultedItemGeoModel<>(Confluence.asResource("armor/silver_armor")));
    }
}
