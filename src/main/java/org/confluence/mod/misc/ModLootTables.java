package org.confluence.mod.misc;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;

public final class ModLootTables {
    public static final ResourceLocation CLAM = register("gameplay/clam");
    public static final ResourceLocation FISHING_LAVA = register("gameplay/fishing/lava");
    public static final ResourceLocation FISH = register("gameplay/fishing/fish");

    private static ResourceLocation register(String id) {
        return new ResourceLocation(Confluence.MODID, id);
    }
}
