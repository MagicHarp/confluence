package org.confluence.mod.client.renderer.item.armor;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.armor.CactusArmorItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SilverArmorRenderer extends GeoArmorRenderer<CactusArmorItem> {
    public SilverArmorRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(Confluence.MODID, "armor/silver_armor")));
    }
}
