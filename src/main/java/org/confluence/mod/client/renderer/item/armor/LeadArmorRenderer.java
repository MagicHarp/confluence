package org.confluence.mod.client.renderer.item.armor;

import org.confluence.mod.Confluence;
import org.confluence.mod.item.armor.CactusArmorItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class LeadArmorRenderer extends GeoArmorRenderer<CactusArmorItem> {
    public LeadArmorRenderer() {
        super(new DefaultedItemGeoModel<>(Confluence.asResource("armor/lead_armor")));
    }
}
