package org.confluence.mod.integration.apothic;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

public class ApothicHelper {
    private static Boolean isAttributesLoaded;
    public static final String ATTRIBUTES_ID = "attributeslib";
    public static final ResourceLocation CRIT_CHANCE = new ResourceLocation(ATTRIBUTES_ID, "crit_chance");

    public static boolean isAttributesLoaded() {
        if (isAttributesLoaded == null) {
            isAttributesLoaded = ModList.get().isLoaded(ATTRIBUTES_ID);
        }
        return isAttributesLoaded;
    }
}
