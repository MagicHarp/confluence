package org.confluence.mod.integration.apothic;

import net.minecraftforge.fml.ModList;

public class ApothicHelper {
    private static Boolean isAttributesLoaded;
    public static final String ATTRIBUTES_ID = "attributeslib";

    public static boolean isAttributesLoaded() {
        if (isAttributesLoaded == null) {
            isAttributesLoaded = ModList.get().isLoaded(ATTRIBUTES_ID);
        }
        return isAttributesLoaded;
    }
}
