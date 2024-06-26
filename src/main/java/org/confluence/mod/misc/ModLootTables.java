package org.confluence.mod.misc;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;

public final class ModLootTables {
    public static final ResourceLocation CLAM = register("gameplay/clam");
    public static final ResourceLocation CHRISTMAS_GIFTS = register("gameplay/christmas_gifts");
    public static final ResourceLocation RED_ENVELOPE = register("gameplay/red_envelope");
    public static final ResourceLocation HERB_BAG = register("gameplay/herb_bag");
    public static final ResourceLocation CAN_OF_WORMS = register("gameplay/can_of_worms");
    public static final ResourceLocation FISHING_LAVA = register("gameplay/fishing/lava");
    public static final ResourceLocation FISH = register("gameplay/fishing");

    private static ResourceLocation register(String id) {
        return new ResourceLocation(Confluence.MODID, id);
    }
}
