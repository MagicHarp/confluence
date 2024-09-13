package org.confluence.mod.client.renderer.item.armor;

import org.confluence.mod.Confluence;
import org.confluence.mod.item.armor.TinArmorItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class TinArmorRenderer extends GeoArmorRenderer<TinArmorItem> {
    public TinArmorRenderer() {
        super(new DefaultedItemGeoModel<>(Confluence.asResource("armor/tin_armor")));
    }
}
