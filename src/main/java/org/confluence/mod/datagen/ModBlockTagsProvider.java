package org.confluence.mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.common.DecorativeBlocks;
import org.confluence.mod.block.common.Torches;
import org.confluence.mod.block.natural.LogBlocks;
import org.confluence.mod.misc.ModTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static org.confluence.mod.Confluence.MODID;
import static org.confluence.mod.block.ModBlocks.*;
import static org.confluence.mod.block.natural.Ores.*;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, @Nullable ExistingFileHelper helper) {
        super(output, lookup, MODID, helper);
    }

    @Override
    public void addTags(HolderLookup.@NotNull Provider provider) {
        LogBlocks.acceptAxeTag(tag(BlockTags.MINEABLE_WITH_AXE));
        IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> mineableWithPickaxe = tag(BlockTags.MINEABLE_WITH_PICKAXE);
        acceptTag(mineableWithPickaxe);
        mineableWithPickaxe.add(
            BIG_RUBY_BLOCK.get(),
            BIG_AMBER_BLOCK.get(),
            BIG_TOPAZ_BLOCK.get(),
            BIG_SAPPHIRE_BLOCK.get(),
            BIG_ANOTHER_AMETHYST_BLOCK.get(),
            DecorativeBlocks.SNOW_BRICKS.get(),
            DecorativeBlocks.ANOTHER_COPPER_BRICKS.get(),
            DecorativeBlocks.ANOTHER_COPPER_PLATE.get(),
            DecorativeBlocks.TIN_BRICKS.get(),
            DecorativeBlocks.TIN_PLATE.get(),
            DecorativeBlocks.ANOTHER_IRON_BRICKS.get(),
            DecorativeBlocks.LEAD_BRICKS.get(),
            DecorativeBlocks.SILVER_BRICKS.get(),
            DecorativeBlocks.TUNGSTEN_BRICKS.get(),
            DecorativeBlocks.ANOTHER_GOLD_BRICKS.get(),
            DecorativeBlocks.PLATINUM_BRICKS.get(),
            DecorativeBlocks.EBONY_ORE_BRICKS.get(),
            DecorativeBlocks.EBONY_ROCK_BRICKS.get(),
            DecorativeBlocks.METEORITE_BRICKS.get(),
            DecorativeBlocks.ANOTHER_CRIMSON_ORE_BRICKS.get(),
            DecorativeBlocks.ANOTHER_CRIMSON_ROCK_BRICKS.get(),
            DecorativeBlocks.PEARL_ROCK_BRICKS.get(),
            DecorativeBlocks.GREEN_CANDY_BLOCK.get(),
            DecorativeBlocks.RED_CANDY_BLOCK.get(),
            DecorativeBlocks.SUN_PLATE.get(),
            DecorativeBlocks.ANOTHER_LAVA_BEAM.get(),
            DecorativeBlocks.ANOTHER_LAVA_BRICKS.get(),
            DecorativeBlocks.ANOTHER_OBSIDIAN_BEAM.get(),
            DecorativeBlocks.ANOTHER_OBSIDIAN_BRICKS.get(),
            DecorativeBlocks.ANOTHER_OBSIDIAN_PLATE.get(),
            DecorativeBlocks.ANOTHER_OBSIDIAN_SMALL_BRICKS.get(),
            DecorativeBlocks.ANOTHER_SMOOTH_OBSIDIAN.get(),
            DecorativeBlocks.ANOTHER_GRANITE_COLUMN.get(),
            DecorativeBlocks.MARBLE_COLUMN.get(),
            DecorativeBlocks.CHISELED_ANOTHER_OBSIDIAN_BRICKS.get(),
            DecorativeBlocks.CRYSTAL_BLOCK.get(),
            DecorativeBlocks.BLUE_BRICK.get(),
            DecorativeBlocks.GREEN_BRICK.get(),
            DecorativeBlocks.PINK_BRICK.get(),
            ModBlocks.RUBY_CHAIN.get(),
            ModBlocks.AMBER_CHAIN.get(),
            ModBlocks.TOPAZ_CHAIN.get(),
            ModBlocks.EMERALD_CHAIN.get(),
            ModBlocks.SAPPHIRE_CHAIN.get(),
            ModBlocks.DIAMOND_CHAIN.get(),
            ModBlocks.AMETHYST_CHAIN.get(),
            ModBlocks.SILK_CHAIN.get(),
            ModBlocks.BONE_CHAIN.get(),
            ModBlocks.EBONY_COBBLESTONE.get(),
            ModBlocks.ANOTHER_CRIMSON_COBBLESTONE.get(),
            ModBlocks.PEARL_COBBLESTONE.get(),
            ModBlocks.HARDENED_SAND_BLOCK.get(),
            ModBlocks.RED_HARDENED_SAND_BLOCK.get(),
            ModBlocks.EBONY_HARDENED_SAND_BLOCK.get(),
            ModBlocks.ANOTHER_CRIMSON_HARDENED_SAND_BLOCK.get(),
            ModBlocks.PEARL_HARDENED_SAND_BLOCK.get()
        );
        tag(BlockTags.DIRT).add(
            CORRUPT_GRASS_BLOCK.get(),
            ASH_BLOCK.get(),
            ANOTHER_CRIMSON_GRASS_BLOCK.get(),
            HALLOW_GRASS_BLOCK.get(),
            ASH_GRASS_BLOCK.get()
        );
        tag(BlockTags.PLANKS).add(
            EBONY_LOG_BLOCKS.PLANKS.get(),
            SHADOW_LOG_BLOCKS.PLANKS.get(),
            PEARL_LOG_BLOCKS.PLANKS.get(),
            PALM_LOG_BLOCKS.PLANKS.get(),
            SPOOKY_LOG_BLOCKS.PLANKS.get(),
            ASH_LOG_BLOCKS.PLANKS.get()
        );
        tag(BlockTags.LOGS).add(
            EBONY_LOG_BLOCKS.LOG.get(),
            SHADOW_LOG_BLOCKS.LOG.get(),
            PEARL_LOG_BLOCKS.LOG.get(),
            PALM_LOG_BLOCKS.LOG.get(),
            ASH_LOG_BLOCKS.LOG.get()
        );
        tag(BlockTags.FENCES).add(
            EBONY_LOG_BLOCKS.FENCE.get(),
            SHADOW_LOG_BLOCKS.FENCE.get(),
            PEARL_LOG_BLOCKS.FENCE.get(),
            PALM_LOG_BLOCKS.FENCE.get(),
            SPOOKY_LOG_BLOCKS.FENCE.get(),
            ASH_LOG_BLOCKS.FENCE.get()
        );
        tag(BlockTags.DIRT).add(
            ANOTHER_CRIMSON_GRASS_BLOCK.get(),
            CORRUPT_GRASS_BLOCK.get(),
            HALLOW_GRASS_BLOCK.get(),
            MUSHROOM_GRASS_BLOCK.get()
        );
        tag(BlockTags.SAND).add(
            ANOTHER_CRIMSON_SAND.get(),
            EBONY_SAND.get(),
            PEARL_SAND.get()
        );
        tag((BlockTags.ICE)).add(
            RED_ICE.get(),
            RED_PACKED_ICE.get(),
            PINK_ICE.get(),
            PINK_PACKED_ICE.get(),
            PURPLE_ICE.get(),
            PURPLE_PACKED_ICE.get()
        );
        tag(BlockTags.NEEDS_DIAMOND_TOOL).add(
            EBONY_ORE.get(), DEEPSLATE_EBONY_ORE.get(), EBONY_BLOCK.get(), RAW_EBONY_BLOCK.get(),
            ANOTHER_CRIMSON_ORE.get(), DEEPSLATE_ANOTHER_CRIMSON_ORE.get(), ANOTHER_CRIMSON_BLOCK.get(), RAW_ANOTHER_CRIMSON_BLOCK.get()
        );
        tag(ModTags.Blocks.NEEDS_4_LEVEL).add(
            HELLSTONE.get(),ASH_HELLSTONE.get(), EBONY_STONE.get(), EBONY_SANDSTONE.get(), ANOTHER_CRIMSON_STONE.get(), ANOTHER_CRIMSON_SANDSTONE.get(),
            PEARL_STONE.get(), PEARL_SANDSTONE.get(),DecorativeBlocks.BLUE_BRICK.get(),DecorativeBlocks.PINK_BRICK.get(),DecorativeBlocks.GREEN_BRICK.get()
        );
        tag(ModTags.Blocks.NEEDS_5_LEVEL).add(
            DEEPSLATE_COBALT_ORE.get(), RAW_COBALT_BLOCK.get(), COBALT_BLOCK.get(),
            DEEPSLATE_PALLADIUM_ORE.get(), RAW_PALLADIUM_BLOCK.get(), PALLADIUM_BLOCK.get()
        );
        tag(ModTags.Blocks.NEEDS_6_LEVEL).add(
            DEEPSLATE_MITHRIL_ORE.get(), RAW_MITHRIL_BLOCK.get(), MITHRIL_BLOCK.get(),
            DEEPSLATE_ORICHALCUM_ORE.get(), RAW_ORICHALCUM_BLOCK.get(), ORICHALCUM_BLOCK.get()
        );
        tag(ModTags.Blocks.NEEDS_7_LEVEL).add(
            DEEPSLATE_ADAMANTITE_ORE.get(), RAW_ADAMANTITE_BLOCK.get(), ADAMANTITE_BLOCK.get(),
            DEEPSLATE_TITANIUM_ORE.get(), RAW_TITANIUM_BLOCK.get(), TITANIUM_BLOCK.get()
        );
        IntrinsicTagAppender<Block> needsNonVanillaLevel = tag(ModTags.Blocks.NEEDS_NON_VANILLA_LEVEL);
        needsNonVanillaLevel.addTag(ModTags.Blocks.NEEDS_4_LEVEL);
        needsNonVanillaLevel.addTag(ModTags.Blocks.NEEDS_5_LEVEL);
        needsNonVanillaLevel.addTag(ModTags.Blocks.NEEDS_6_LEVEL);
        needsNonVanillaLevel.addTag(ModTags.Blocks.NEEDS_7_LEVEL);
        tag(ModTags.Blocks.FLOWER_BOOTS_AVAILABLE).add(
            Blocks.GRASS_BLOCK,
            HALLOW_GRASS_BLOCK.get()
        );
        IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> torch = tag(ModTags.Blocks.TORCH);
        torch.add(Blocks.TORCH, Blocks.WALL_TORCH, Blocks.SOUL_TORCH, Blocks.SOUL_WALL_TORCH);
        for (Torches torches : Torches.values()) torch.add(torches.stand.get(), torches.wall.get());
    }
}
