package org.confluence.mod.client.renderer.item.armor;

import org.confluence.mod.Confluence;
import org.confluence.mod.item.armor.PlankArmorItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class PlankArmorRenderer extends GeoArmorRenderer<PlankArmorItem> {
    public PlankArmorRenderer() {
        super(new DefaultedItemGeoModel<>(Confluence.asResource("armor/plank_armor")));
    }
}
