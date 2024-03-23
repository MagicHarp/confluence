package org.confluence.mod.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;
import org.confluence.mod.item.common.Materials;

import java.util.List;

import static org.confluence.mod.Confluence.MODID;

public class ConfluenceTiers {
    public static final ForgeTier CACTUS = new ForgeTier(1, 272, 4, 1, 5, BlockTags.FLOWERS, () -> Ingredient.of(Items.CACTUS));
    public static final ForgeTier COPPER = new ForgeTier(1, 250, 4, 1, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.COPPER_INGOT));
    public static final ForgeTier TIN = new ForgeTier(1, 270, 4, 1, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Materials.TIN_INGOT.get()));
    public static final ForgeTier LEAD = new ForgeTier(2, 286, 6, 2, 14, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Materials.LEAD_INGOT.get()));
    public static final ForgeTier SILVER = new ForgeTier(2, 304, 6, 2, 14, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Materials.SILVER_INGOT.get()));
    public static final ForgeTier TUNGSTEN = new ForgeTier(2, 648, 8, 2, 18, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Materials.TUNGSTEN_INGOT.get()));
    public static final ForgeTier GOLD = new ForgeTier(3, 1600, 8, 3, 22, Tags.Blocks.NEEDS_GOLD_TOOL, () -> Ingredient.of(Items.GOLD_INGOT));
    public static final ForgeTier PLATINUM = new ForgeTier(3, 1661, 8, 3, 22, Tags.Blocks.NEEDS_GOLD_TOOL, () -> Ingredient.of(Materials.PLATINUM_INGOT.get()));
    public static final ForgeTier EBONY = new ForgeTier(4, 0, 10, 4, 24, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Materials.EBONY_INGOT.get()));
    public static final ForgeTier ANOTHER_CRIMSON = new ForgeTier(4, 0, 11, 4, 25, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Materials.ANOTHER_CRIMSON_INGOT.get()));
    public static final ForgeTier HELLSTONE = new ForgeTier(5, 0, 14, 6, 27, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Materials.HELLSTONE_INGOT.get()));
    public static final ForgeTier COBALT = new ForgeTier(4, 270, 4, 1, 5, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Materials.COBALT_INGOT.get()));

    public static final ForgeTier PALLADIUM = new ForgeTier(1, 270, 4, 1, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Materials.PALLADIUM_INGOT.get()));
    public static final ForgeTier MITHRIL = new ForgeTier(1, 270, 4, 1, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Materials.MITHRIL_INGOT.get()));
    public static final ForgeTier ORICHALCUM = new ForgeTier(1, 270, 4, 1, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Materials.ORICHALCUM_INGOT.get()));
    public static final ForgeTier ADAMANTITE = new ForgeTier(1, 270, 4, 1, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Materials.ADAMANTITE_INGOT.get()));
    public static final ForgeTier TITANIUM = new ForgeTier(1, 270, 4, 1, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Materials.TITANIUM_INGOT.get()));

    public static void register() {
        ResourceLocation copper = new ResourceLocation(MODID, "copper");
        ResourceLocation tin = new ResourceLocation(MODID, "tin");
        ResourceLocation iron = new ResourceLocation("iron");
        ResourceLocation lead = new ResourceLocation(MODID, "lead");
        ResourceLocation silver = new ResourceLocation(MODID, "silver");
        ResourceLocation tungsten = new ResourceLocation(MODID, "wolfram");
        ResourceLocation gold = new ResourceLocation(MODID, "gold");
        ResourceLocation platinum = new ResourceLocation(MODID, "platinum");
        ResourceLocation ebony = new ResourceLocation(MODID, "ebony");
        ResourceLocation another_crimson = new ResourceLocation(MODID, "another_crimson");
        ResourceLocation hellstone = new ResourceLocation(MODID, "hellstone");
        ResourceLocation cobalt = new ResourceLocation(MODID, "cobalt");
        ResourceLocation palladium = new ResourceLocation(MODID, "palladium");
        ResourceLocation mithril = new ResourceLocation(MODID, "mithril");
        ResourceLocation orichalcum = new ResourceLocation(MODID, "orichalcum");
        ResourceLocation adamantite = new ResourceLocation(MODID, "adamantite");
        ResourceLocation titanium = new ResourceLocation(MODID, "titanium");
        TierSortingRegistry.registerTier(COPPER, copper, List.of(new ResourceLocation("stone")), List.of(iron));
        TierSortingRegistry.registerTier(TIN, tin, List.of(copper), List.of(lead));
        TierSortingRegistry.registerTier(LEAD, lead, List.of(tin, iron), List.of(silver));
        TierSortingRegistry.registerTier(SILVER, silver, List.of(lead), List.of(tungsten));
        TierSortingRegistry.registerTier(TUNGSTEN, tungsten, List.of(silver), List.of(gold));
        TierSortingRegistry.registerTier(GOLD, gold, List.of(tungsten), List.of(platinum));
        TierSortingRegistry.registerTier(PLATINUM, platinum, List.of(gold), List.of(ebony,another_crimson));
        TierSortingRegistry.registerTier(EBONY,ebony, List.of(platinum), List.of(hellstone));
        TierSortingRegistry.registerTier(ANOTHER_CRIMSON, another_crimson, List.of(platinum), List.of(hellstone));
        TierSortingRegistry.registerTier(HELLSTONE,hellstone, List.of(ebony,another_crimson), List.of(cobalt));
        TierSortingRegistry.registerTier(COBALT, cobalt, List.of(hellstone), List.of(palladium));
        TierSortingRegistry.registerTier(PALLADIUM, palladium, List.of(cobalt), List.of(mithril));
        TierSortingRegistry.registerTier(MITHRIL, mithril, List.of(palladium), List.of(orichalcum));
        TierSortingRegistry.registerTier(ORICHALCUM, orichalcum, List.of(mithril), List.of(adamantite));
        TierSortingRegistry.registerTier(ADAMANTITE, adamantite, List.of(orichalcum), List.of(titanium));
        TierSortingRegistry.registerTier(TITANIUM, titanium, List.of(adamantite), List.of());
    }
}
