package org.confluence.mod.client.renderer.item.armor;

import org.confluence.mod.Confluence;
import org.confluence.mod.item.armor.CopperArmorItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class CopperArmorRenderer extends GeoArmorRenderer<CopperArmorItem> {
    public CopperArmorRenderer() {
        super(new DefaultedItemGeoModel<>(Confluence.asResource("armor/copper_armor")));
    }
}
