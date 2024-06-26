package org.confluence.mod.client.renderer.item.armor;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.armor.SnowPinkArmorItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SnowPinkArmorRenderer extends GeoArmorRenderer<SnowPinkArmorItem> {
    public SnowPinkArmorRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(Confluence.MODID, "armor/snow_pink_armor")));
    }
}
