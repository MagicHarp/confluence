package org.confluence.mod.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

import static org.confluence.mod.Confluence.MODID;

public class ConfluenceTiers {
    public static final ForgeTier COPPER = new ForgeTier(1, 250, 4, 1, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.COPPER_INGOT));
    public static final ForgeTier TIN = new ForgeTier(1, 270, 4, 1, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ConfluenceItems.Materials.TIN_INGOT.get()));
    public static final ForgeTier LEAD = new ForgeTier(2, 286, 6, 2, 14, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(ConfluenceItems.Materials.LEAD_INGOT.get()));
    public static final ForgeTier SILVER = new ForgeTier(2, 304, 6, 2, 14, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(ConfluenceItems.Materials.SILVER_INGOT.get()));
    public static final ForgeTier WOLFRAM = new ForgeTier(2, 648, 8, 2, 18, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(ConfluenceItems.Materials.WOLFRAM_INGOT.get()));
    public static final ForgeTier GOLD = new ForgeTier(3, 1600, 8, 3, 22, Tags.Blocks.NEEDS_GOLD_TOOL, () -> Ingredient.of(Items.GOLD_INGOT));
    public static final ForgeTier PLATINUM = new ForgeTier(3, 1661, 8, 3, 22, Tags.Blocks.NEEDS_GOLD_TOOL, () -> Ingredient.of(ConfluenceItems.Materials.PLATINUM_INGOT.get()));
    public static final ForgeTier COBALT = new ForgeTier(1, 270, 4, 1, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ConfluenceItems.Materials.COBALT_INGOT.get()));
    public static final ForgeTier PALLADIUM = new ForgeTier(1, 270, 4, 1, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ConfluenceItems.Materials.PALLADIUM_INGOT.get()));
    public static final ForgeTier MITHRIL = new ForgeTier(1, 270, 4, 1, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ConfluenceItems.Materials.MITHRIL_INGOT.get()));
    public static final ForgeTier ORICHALCUM = new ForgeTier(1, 270, 4, 1, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ConfluenceItems.Materials.ORICHALCUM_INGOT.get()));
    public static final ForgeTier ADAMANTITE = new ForgeTier(1, 270, 4, 1, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ConfluenceItems.Materials.ADAMANTITE_INGOT.get()));
    public static final ForgeTier TITANIUM = new ForgeTier(1, 270, 4, 1, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ConfluenceItems.Materials.TITANIUM_INGOT.get()));

    public static void register() {
        ResourceLocation copper = new ResourceLocation(MODID, "copper");
        ResourceLocation tin = new ResourceLocation(MODID, "tin");
        TierSortingRegistry.registerTier(COPPER, copper, List.of(new ResourceLocation("stone")), List.of(new ResourceLocation("iron")));
        TierSortingRegistry.registerTier(TIN, tin, List.of(), List.of());
        TierSortingRegistry.registerTier(LEAD, new ResourceLocation(MODID, "lead"), List.of(), List.of());
        TierSortingRegistry.registerTier(SILVER, new ResourceLocation(MODID, "silver"), List.of(), List.of());
        TierSortingRegistry.registerTier(WOLFRAM, new ResourceLocation(MODID, "wolfram"), List.of(), List.of());
        TierSortingRegistry.registerTier(GOLD, new ResourceLocation(MODID, "gold"), List.of(), List.of());
        TierSortingRegistry.registerTier(PLATINUM, new ResourceLocation(MODID, "platinum"), List.of(), List.of());
        TierSortingRegistry.registerTier(COBALT, new ResourceLocation(MODID, "cobalt"), List.of(), List.of());
        TierSortingRegistry.registerTier(PALLADIUM, new ResourceLocation(MODID, "palladium"), List.of(), List.of());
        TierSortingRegistry.registerTier(MITHRIL, new ResourceLocation(MODID, "mithril"), List.of(), List.of());
        TierSortingRegistry.registerTier(ORICHALCUM, new ResourceLocation(MODID, "orichalcum"), List.of(), List.of());
        TierSortingRegistry.registerTier(ADAMANTITE, new ResourceLocation(MODID, "adamantite"), List.of(), List.of());
        TierSortingRegistry.registerTier(TITANIUM, new ResourceLocation(MODID, "titanium"), List.of(), List.of());
    }
}
