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
    public static final ResourceLocation EXTRACT_DESERT_FOSSIL = register("gameplay/extract/with_desert_fossil");
    public static final ResourceLocation EXTRACT_GRAVEL = register("gameplay/extract/with_gravel");
    public static final ResourceLocation EXTRACT_JUNK = register("gameplay/extract/with_junk");
    public static final ResourceLocation EXTRACT_SLUSH = register("gameplay/extract/with_slush");
    public static final ResourceLocation EXTRACT_MARINE_GRAVEL = register("gameplay/extract/with_marine_gravel");

    private static ResourceLocation register(String id) {
        return new ResourceLocation(Confluence.MODID, id);
    }
}
