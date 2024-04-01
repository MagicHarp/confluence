package org.confluence.mod.client.renderer.item.armor;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.armor.CactusArmorItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class CopperArmorRenderer extends GeoArmorRenderer<CactusArmorItem> {
    public CopperArmorRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(Confluence.MODID, "armor/copper_armor")));
    }
}
