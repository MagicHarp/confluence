package org.confluence.mod.integration.apothic;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

public interface ApothicHelper {
    String ATTRIBUTES_ID = "attributeslib";
    ResourceLocation CRIT_CHANCE = new ResourceLocation(ATTRIBUTES_ID, "crit_chance");

    static boolean isAttributesLoaded() {
        return ModList.get().isLoaded(ATTRIBUTES_ID);
    }
}
