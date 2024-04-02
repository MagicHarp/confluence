package org.confluence.mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.confluence.mod.ModTags;
import org.confluence.mod.block.DecorativeBlocks;
import org.confluence.mod.block.LogBlocks;
import org.confluence.mod.block.Ores;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static org.confluence.mod.Confluence.MODID;
import static org.confluence.mod.block.ModBlocks.*;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, @Nullable ExistingFileHelper helper) {
        super(output, lookup, MODID, helper);
    }

    @Override
    public void addTags(HolderLookup.@NotNull Provider provider) {
        LogBlocks.acceptAxeTag(tag(BlockTags.MINEABLE_WITH_AXE));
        IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> mineableWithPickaxe = tag(BlockTags.MINEABLE_WITH_PICKAXE);
        Ores.acceptTag(mineableWithPickaxe);
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
            DecorativeBlocks.CRYSTAL_BLOCK.get()



        );


        tag(BlockTags.NEEDS_DIAMOND_TOOL).add(
            Ores.EBONY_ORE.get(), Ores.DEEPSLATE_EBONY_ORE.get(), Ores.EBONY_BLOCK.get(), Ores.RAW_EBONY_BLOCK.get(),
            Ores.ANOTHER_CRIMSON_ORE.get(), Ores.DEEPSLATE_ANOTHER_CRIMSON_ORE.get(), Ores.ANOTHER_CRIMSON_BLOCK.get(), Ores.RAW_ANOTHER_CRIMSON_BLOCK.get()
        );

        tag(ModTags.NEEDS_4_LEVEL).add(
            Ores.HELLSTONE.get()
        );
        tag(ModTags.NEEDS_5_LEVEL).add(
            Ores.DEEPSLATE_COBALT_ORE.get(), Ores.RAW_COBALT_BLOCK.get(), Ores.COBALT_BLOCK.get(),
            Ores.DEEPSLATE_PALLADIUM_ORE.get(), Ores.RAW_PALLADIUM_BLOCK.get(), Ores.PALLADIUM_BLOCK.get()
        );
        tag(ModTags.NEEDS_6_LEVEL).add(
            Ores.DEEPSLATE_MITHRIL_ORE.get(), Ores.RAW_MITHRIL_BLOCK.get(), Ores.MITHRIL_BLOCK.get(),
            Ores.DEEPSLATE_ORICHALCUM_ORE.get(), Ores.RAW_ORICHALCUM_BLOCK.get(), Ores.ORICHALCUM_BLOCK.get()
        );
        tag(ModTags.NEEDS_7_LEVEL).add(
            Ores.DEEPSLATE_ADAMANTITE_ORE.get(), Ores.RAW_ADAMANTITE_BLOCK.get(), Ores.ADAMANTITE_BLOCK.get(),
            Ores.DEEPSLATE_TITANIUM_ORE.get(), Ores.RAW_TITANIUM_BLOCK.get(), Ores.TITANIUM_BLOCK.get()
        );
    }
}
