package org.confluence.mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.confluence.mod.block.EnumBlockRegister;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.common.DecorativeBlocks;
import org.confluence.mod.block.common.Torches;
import org.confluence.mod.block.natural.LogBlocks;
import org.confluence.mod.block.natural.Ores;
import org.confluence.mod.misc.ModTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import static org.confluence.mod.Confluence.MODID;
import static org.confluence.mod.block.ModBlocks.*;
import static org.confluence.mod.block.natural.Ores.*;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, @Nullable ExistingFileHelper helper) {
        super(output, lookup, MODID, helper);
    }

    /**
     * 批量为方块的注册信息添加内部对应的tag
     *
     * @param enumBlocks EnumBlockRegister类型的方块注册信息
     */
    @SafeVarargs
    public final void addTagsForBlocks(EnumBlockRegister<Block>... enumBlocks) {
        HashMap<TagKey<Block>, IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block>> memoi = new HashMap<>();
        for (EnumBlockRegister<Block> enumBlock : enumBlocks) {
            for (TagKey<Block> tagKey : enumBlock.getBlockTags().getTags()) {
                memoi.computeIfAbsent(tagKey, this::tag).add(enumBlock.get());
            }
        }
    }

    @Override
    public void addTags(HolderLookup.@NotNull Provider provider) {
        // 木制方块
        LogBlocks.acceptAxeTag(tag(BlockTags.MINEABLE_WITH_AXE));
        // 矿物方块
        addTagsForBlocks(Ores.values());
        // 其他需要镐挖掘的方块
        IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> mineableWithPickaxe = tag(BlockTags.MINEABLE_WITH_PICKAXE);
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
            ModBlocks.PEARL_HARDENED_SAND_BLOCK.get(),
            ModBlocks.DESERT_FOSSIL.get(),
            ModBlocks.EXTRACTINATOR.get(),
            ModBlocks.DART_TRAP.get(),
            ModBlocks.SIGNAL_ADAPTER.get(),
            ModBlocks.SWITCH.get(),
            ModBlocks.TIMERS_BLOCK_1_1.get(),
            ModBlocks.TIMERS_BLOCK_3_1.get(),
            ModBlocks.TIMERS_BLOCK_5_1.get(),
            ModBlocks.TIMERS_BLOCK_1_2.get(),
            ModBlocks.TIMERS_BLOCK_1_4.get()
        );
        // 其他需要铲挖掘的方块
        IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> mineableWithShovel = tag(BlockTags.MINEABLE_WITH_SHOVEL);
        mineableWithShovel.add(
            SLUSH.get(),
            EBONY_SAND.get(),
            PEARL_SAND.get(),
            ANOTHER_CRIMSON_SAND.get(),
            EBONY_SAND_LAYER_BLOCK.get(),
            PEARL_SAND_LAYER_BLOCK.get(),
            ANOTHER_CRIMSON_SAND_LAYER_BLOCK.get(),
            ASH_BLOCK.get()
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
        tag((BlockTags.STONE_ORE_REPLACEABLES)).add(
            HARDENED_SAND_BLOCK.get(),
            RED_HARDENED_SAND_BLOCK.get(),
            EBONY_STONE.get(),
            PEARL_STONE.get(),
            ANOTHER_CRIMSON_STONE.get()
        );

        tag(ModTags.Blocks.NEEDS_4_LEVEL).add(
            EBONY_STONE.get(), EBONY_SANDSTONE.get(), ANOTHER_CRIMSON_STONE.get(), ANOTHER_CRIMSON_SANDSTONE.get(),
            PEARL_STONE.get(), PEARL_SANDSTONE.get(), DecorativeBlocks.BLUE_BRICK.get(), DecorativeBlocks.PINK_BRICK.get(), DecorativeBlocks.GREEN_BRICK.get()
        );
        IntrinsicTagAppender<Block> needsNonVanillaLevel = tag(ModTags.Blocks.NEEDS_NON_VANILLA_LEVEL);
        needsNonVanillaLevel.addTag(ModTags.Blocks.NEEDS_4_LEVEL);
        needsNonVanillaLevel.addTag(ModTags.Blocks.NEEDS_5_LEVEL);
        needsNonVanillaLevel.addTag(ModTags.Blocks.NEEDS_6_LEVEL);
        needsNonVanillaLevel.addTag(ModTags.Blocks.NEEDS_7_LEVEL);
        needsNonVanillaLevel.addTag(ModTags.Blocks.NEEDS_8_LEVEL);
        tag(ModTags.Blocks.FLOWER_BOOTS_AVAILABLE).add(
            Blocks.GRASS_BLOCK,
            HALLOW_GRASS_BLOCK.get()
        );
        IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> torch = tag(ModTags.Blocks.TORCH);
        torch.add(Blocks.TORCH, Blocks.WALL_TORCH, Blocks.SOUL_TORCH, Blocks.SOUL_WALL_TORCH);
        for (Torches torches : Torches.values()) torch.add(torches.stand.get(), torches.wall.get());
        tag(ModTags.Blocks.POTS_SURVIVE).add(
            Blocks.STONE, Blocks.DIRT, Blocks.SANDSTONE, Blocks.MOSS_BLOCK,
            ModBlocks.EBONY_STONE.get(), ModBlocks.EBONY_STONE.get(), ModBlocks.ANOTHER_CRIMSON_STONE.get(), ModBlocks.HARDENED_SAND_BLOCK.get(), ModBlocks.RED_HARDENED_SAND_BLOCK.get()
        );
        tag(ModTags.Blocks.COIN_PILE).add(COPPER_COIN_PILE.get(), SILVER_COIN_PILE.get(), GOLDEN_COIN_PILE.get(), PLATINUM_COIN_PILE.get());
        tag(ModTags.Blocks.EASY_CRASH).add(THIN_ICE_BLOCK.get(), CRACKED_BLUE_BRICK.get(), CRACKED_GREEN_BRICK.get(), CRACKED_PINK_BRICK.get());
    }
}
