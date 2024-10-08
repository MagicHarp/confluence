package org.confluence.mod.block.natural.spreadable;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.natural.Ores;
import org.confluence.mod.command.ConfluenceData;
import org.jetbrains.annotations.NotNull;

import java.util.Hashtable;
import java.util.Map;
import java.util.function.Supplier;

public interface ISpreadable {
    // That was a joke haha!
    BooleanProperty STILL_ALIVE = BooleanProperty.create("still_alive");

    Type getType();

    default void spread(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
        if (!blockState.getValue(STILL_ALIVE)) return;
        if (randomSource.nextInt(100) >= serverLevel.getGameRules().getInt(Confluence.SPREADABLE_CHANCE)) return;
        int phase = ConfluenceData.get(serverLevel).getGamePhase().ordinal();
        for (int i = 0; i < 4; ++i) {
            BlockPos targetPos = blockPos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(5) - 3, randomSource.nextInt(3) - 1);
            BlockState beforeTransformState = serverLevel.getBlockState(targetPos);
            Block targetBlock = getType().blockMap.get(beforeTransformState.getBlock());
            if (targetBlock == null || beforeTransformState.is(Blocks.GRASS) || beforeTransformState.is(Blocks.FERN) || beforeTransformState.is(Blocks.TALL_GRASS)) {
                continue; // 不要直接传播草
            }
            if (beforeTransformState.is(Blocks.DIRT) || beforeTransformState.is(ModBlocks.ASH_BLOCK.get())) {
                if (!isFullBlock(serverLevel, targetPos.above())) {
                    spreadOrDie(phase, blockState, serverLevel, blockPos, randomSource, targetBlock.defaultBlockState(), targetPos);
                }
            } else {
                spreadOrDie(phase, blockState, serverLevel, blockPos, randomSource, targetBlock.defaultBlockState(), targetPos);
            }
            BlockState above = serverLevel.getBlockState(targetPos.above());
            if (above.is(Blocks.GRASS) || above.is(Blocks.FERN) || above.is(Blocks.TALL_GRASS)) {  // 被动传播草
                targetBlock = getType().blockMap.get(above.getBlock());
                serverLevel.setBlockAndUpdate(targetPos.above(), targetBlock == null ? above : targetBlock.defaultBlockState());
            }

        }
    }

    static boolean isFullBlock(ServerLevel serverLevel, BlockPos pos) {
        return Block.isShapeFullBlock(serverLevel.getBlockState(pos).getCollisionShape(serverLevel, pos));
    }

    static void spreadOrDie(int phase, BlockState selfState, ServerLevel serverLevel, BlockPos selfPos, RandomSource randomSource, BlockState targetState, BlockPos targetPos) {
        serverLevel.setBlockAndUpdate(targetPos, targetState);
        if (randomSource.nextInt(7) > phase) {
            serverLevel.setBlockAndUpdate(selfPos, selfState.setValue(STILL_ALIVE, false));
        }
    }

    // 到时候溶液也用这个
    enum Type {
        HALLOW(
            () -> Blocks.DIRT, ModBlocks.HALLOW_GRASS_BLOCK,
            // 原木
            () -> Blocks.OAK_LOG, ModBlocks.PEARL_LOG_BLOCKS.LOG,
            () -> Blocks.ACACIA_LOG, ModBlocks.PEARL_LOG_BLOCKS.LOG,
            () -> Blocks.BIRCH_LOG, ModBlocks.PEARL_LOG_BLOCKS.LOG,
            () -> Blocks.CHERRY_LOG, ModBlocks.PEARL_LOG_BLOCKS.LOG,
            () -> Blocks.JUNGLE_LOG, ModBlocks.PEARL_LOG_BLOCKS.LOG,
            () -> Blocks.DARK_OAK_LOG, ModBlocks.PEARL_LOG_BLOCKS.LOG,
            () -> Blocks.MANGROVE_LOG, ModBlocks.PEARL_LOG_BLOCKS.LOG,
            () -> Blocks.SPRUCE_LOG, ModBlocks.PEARL_LOG_BLOCKS.LOG,
            ModBlocks.PALM_LOG_BLOCKS.LOG, ModBlocks.PEARL_LOG_BLOCKS.LOG,
            ModBlocks.EBONY_LOG_BLOCKS.LOG, ModBlocks.PEARL_LOG_BLOCKS.LOG,
            ModBlocks.SHADOW_LOG_BLOCKS.LOG, ModBlocks.PEARL_LOG_BLOCKS.LOG,
            // 树皮
            () -> Blocks.OAK_WOOD, ModBlocks.PEARL_LOG_BLOCKS.WOOD,
            () -> Blocks.ACACIA_WOOD, ModBlocks.PEARL_LOG_BLOCKS.WOOD,
            () -> Blocks.BIRCH_WOOD, ModBlocks.PEARL_LOG_BLOCKS.WOOD,
            () -> Blocks.CHERRY_WOOD, ModBlocks.PEARL_LOG_BLOCKS.WOOD,
            () -> Blocks.JUNGLE_WOOD, ModBlocks.PEARL_LOG_BLOCKS.WOOD,
            () -> Blocks.DARK_OAK_WOOD, ModBlocks.PEARL_LOG_BLOCKS.WOOD,
            () -> Blocks.MANGROVE_WOOD, ModBlocks.PEARL_LOG_BLOCKS.WOOD,
            () -> Blocks.SPRUCE_WOOD, ModBlocks.PEARL_LOG_BLOCKS.WOOD,
            ModBlocks.PALM_LOG_BLOCKS.WOOD, ModBlocks.PEARL_LOG_BLOCKS.WOOD,
            ModBlocks.EBONY_LOG_BLOCKS.WOOD, ModBlocks.PEARL_LOG_BLOCKS.WOOD,
            ModBlocks.SHADOW_LOG_BLOCKS.WOOD, ModBlocks.PEARL_LOG_BLOCKS.WOOD,
            // 去皮原木
            () -> Blocks.STRIPPED_ACACIA_LOG, ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_LOG,
            () -> Blocks.STRIPPED_CHERRY_LOG, ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_LOG,
            () -> Blocks.STRIPPED_BIRCH_LOG, ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_LOG,
            () -> Blocks.STRIPPED_DARK_OAK_LOG, ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_LOG,
            () -> Blocks.STRIPPED_OAK_LOG, ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_LOG,
            () -> Blocks.STRIPPED_MANGROVE_LOG, ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_LOG,
            () -> Blocks.STRIPPED_SPRUCE_LOG, ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_LOG,
            ModBlocks.PALM_LOG_BLOCKS.STRIPPED_LOG, ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_LOG,
            ModBlocks.EBONY_LOG_BLOCKS.STRIPPED_LOG, ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_LOG,
            ModBlocks.SHADOW_LOG_BLOCKS.STRIPPED_LOG, ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_LOG,
            // 去皮树皮
            () -> Blocks.STRIPPED_ACACIA_WOOD, ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_WOOD,
            () -> Blocks.STRIPPED_CHERRY_WOOD, ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_WOOD,
            () -> Blocks.STRIPPED_BIRCH_WOOD, ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_WOOD,
            () -> Blocks.STRIPPED_DARK_OAK_WOOD, ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_WOOD,
            () -> Blocks.STRIPPED_OAK_WOOD, ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_WOOD,
            () -> Blocks.STRIPPED_MANGROVE_WOOD, ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_WOOD,
            () -> Blocks.STRIPPED_SPRUCE_WOOD, ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_WOOD,
            ModBlocks.PALM_LOG_BLOCKS.STRIPPED_WOOD, ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_WOOD,
            ModBlocks.EBONY_LOG_BLOCKS.STRIPPED_WOOD, ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_WOOD,
            ModBlocks.SHADOW_LOG_BLOCKS.STRIPPED_WOOD, ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_WOOD,
            // 树叶
            () -> Blocks.OAK_LEAVES, ModBlocks.PEARL_LOG_BLOCKS.LEAVES,
            () -> Blocks.ACACIA_LEAVES, ModBlocks.PEARL_LOG_BLOCKS.LEAVES,
            () -> Blocks.BIRCH_LEAVES, ModBlocks.PEARL_LOG_BLOCKS.LEAVES,
            () -> Blocks.CHERRY_LEAVES, ModBlocks.PEARL_LOG_BLOCKS.LEAVES,
            () -> Blocks.JUNGLE_LEAVES, ModBlocks.PEARL_LOG_BLOCKS.LEAVES,
            () -> Blocks.DARK_OAK_LEAVES, ModBlocks.PEARL_LOG_BLOCKS.LEAVES,
            () -> Blocks.MANGROVE_LEAVES, ModBlocks.PEARL_LOG_BLOCKS.LEAVES,
            () -> Blocks.SPRUCE_LEAVES, ModBlocks.PEARL_LOG_BLOCKS.LEAVES,
            ModBlocks.PALM_LOG_BLOCKS.LEAVES, ModBlocks.PEARL_LOG_BLOCKS.LEAVES,
            ModBlocks.EBONY_LOG_BLOCKS.LEAVES, ModBlocks.PEARL_LOG_BLOCKS.LEAVES,
            ModBlocks.SHADOW_LOG_BLOCKS.LEAVES, ModBlocks.PEARL_LOG_BLOCKS.LEAVES,

            // 原版环境方块
            () -> Blocks.GRASS_BLOCK, ModBlocks.HALLOW_GRASS_BLOCK,
            () -> Blocks.STONE, ModBlocks.PEARL_STONE,
            () -> Blocks.COBBLESTONE, ModBlocks.PEARL_COBBLESTONE,
            () -> Blocks.SANDSTONE, ModBlocks.PEARL_SANDSTONE,
            () -> Blocks.SAND, ModBlocks.PEARL_SAND,
            () -> Blocks.GRASS, ModBlocks.HALLOW_GRASS,
            () -> Blocks.TALL_GRASS, ModBlocks.HALLOW_GRASS,
            () -> Blocks.ICE, ModBlocks.PINK_ICE,
            () -> Blocks.PACKED_ICE, ModBlocks.PINK_PACKED_ICE,
            // 邪恶环境方块
            ModBlocks.TR_CRIMSON_GRASS_BLOCK, ModBlocks.HALLOW_GRASS_BLOCK,
            ModBlocks.CORRUPT_GRASS_BLOCK, ModBlocks.HALLOW_GRASS_BLOCK,

            ModBlocks.EBONY_STONE, ModBlocks.PEARL_STONE,
            ModBlocks.TR_CRIMSON_STONE, ModBlocks.PEARL_STONE,

            ModBlocks.EBONY_COBBLESTONE, ModBlocks.PEARL_COBBLESTONE,
            ModBlocks.TR_CRIMSON_COBBLESTONE, ModBlocks.PEARL_COBBLESTONE,

            ModBlocks.HARDENED_SAND_BLOCK, ModBlocks.PEARL_HARDENED_SAND_BLOCK,
            ModBlocks.EBONY_HARDENED_SAND_BLOCK, ModBlocks.PEARL_HARDENED_SAND_BLOCK,
            ModBlocks.TR_CRIMSON_HARDENED_SAND_BLOCK, ModBlocks.PEARL_HARDENED_SAND_BLOCK,

            ModBlocks.EBONY_SANDSTONE, ModBlocks.PEARL_SANDSTONE,
            ModBlocks.TR_CRIMSON_SANDSTONE, ModBlocks.PEARL_SANDSTONE,

            ModBlocks.PURPLE_ICE, ModBlocks.PINK_ICE,
            ModBlocks.PURPLE_PACKED_ICE, ModBlocks.PINK_PACKED_ICE,
            ModBlocks.RED_ICE, ModBlocks.PINK_ICE,
            ModBlocks.RED_PACKED_ICE, ModBlocks.PINK_PACKED_ICE,
            // 蘑菇
            ModBlocks.TR_CRIMSON_MUSHROOM, ModBlocks.LIFE_MUSHROOM,
            ModBlocks.EBONY_MUSHROOM, ModBlocks.LIFE_MUSHROOM,
            //矿物
            () -> Blocks.REDSTONE_ORE, ModBlocks.SANCTIFICATION_REDSTONE_ORE,
            () -> Blocks.COAL_ORE, Ores.SANCTIFICATION_COAL_ORE,
            () -> Blocks.LAPIS_ORE, Ores.SANCTIFICATION_LAPIS_ORE,
            () -> Blocks.COPPER_ORE, Ores.SANCTIFICATION_COPPER_ORE,
            () -> Blocks.IRON_ORE, Ores.SANCTIFICATION_IRON_ORE,
            () -> Blocks.EMERALD_ORE, Ores.SANCTIFICATION_EMERALD_ORE,
            () -> Blocks.DIAMOND_ORE, Ores.SANCTIFICATION_DIAMOND_ORE,
            () -> Blocks.GOLD_ORE, Ores.SANCTIFICATION_GOLD_ORE,
            Ores.TIN_ORE, Ores.SANCTIFICATION_TIN_ORE,
            Ores.LEAD_ORE, Ores.SANCTIFICATION_LEAD_ORE,
            Ores.SILVER_ORE, Ores.SANCTIFICATION_SILVER_ORE,
            Ores.TUNGSTEN_ORE, Ores.SANCTIFICATION_TUNGSTEN_ORE,
            Ores.PLATINUM_ORE, Ores.SANCTIFICATION_PLATINUM_ORE,

            Ores.CORRUPTION_TIN_ORE, Ores.SANCTIFICATION_TIN_ORE,
            Ores.CORRUPTION_LEAD_ORE, Ores.SANCTIFICATION_LEAD_ORE,
            Ores.CORRUPTION_SILVER_ORE, Ores.SANCTIFICATION_SILVER_ORE,
            Ores.CORRUPTION_TUNGSTEN_ORE, Ores.SANCTIFICATION_TUNGSTEN_ORE,
            Ores.CORRUPTION_PLATINUM_ORE, Ores.SANCTIFICATION_PLATINUM_ORE,
            Ores.CORRUPTION_COAL_ORE, Ores.SANCTIFICATION_COAL_ORE,
            Ores.CORRUPTION_COPPER_ORE, Ores.SANCTIFICATION_COPPER_ORE,
            Ores.CORRUPTION_IRON_ORE, Ores.SANCTIFICATION_IRON_ORE,
            Ores.CORRUPTION_GOLD_ORE, Ores.SANCTIFICATION_GOLD_ORE,
            Ores.CORRUPTION_DIAMOND_ORE, Ores.SANCTIFICATION_DIAMOND_ORE,
            ModBlocks.CORRUPTION_REDSTONE_ORE, ModBlocks.SANCTIFICATION_REDSTONE_ORE,
            Ores.FLESHIFICATION_TIN_ORE, Ores.SANCTIFICATION_TIN_ORE,
            Ores.FLESHIFICATION_LEAD_ORE, Ores.SANCTIFICATION_LEAD_ORE,
            Ores.FLESHIFICATION_SILVER_ORE, Ores.SANCTIFICATION_SILVER_ORE,
            Ores.FLESHIFICATION_TUNGSTEN_ORE, Ores.SANCTIFICATION_TUNGSTEN_ORE,
            Ores.FLESHIFICATION_PLATINUM_ORE, Ores.SANCTIFICATION_PLATINUM_ORE,
            Ores.FLESHIFICATION_COAL_ORE, Ores.SANCTIFICATION_COAL_ORE,
            Ores.FLESHIFICATION_COPPER_ORE, Ores.SANCTIFICATION_COPPER_ORE,
            Ores.FLESHIFICATION_IRON_ORE, Ores.SANCTIFICATION_IRON_ORE,
            Ores.FLESHIFICATION_GOLD_ORE, Ores.SANCTIFICATION_GOLD_ORE,
            Ores.FLESHIFICATION_DIAMOND_ORE, Ores.SANCTIFICATION_DIAMOND_ORE,
            ModBlocks.FLESHIFICATION_REDSTONE_ORE, ModBlocks.SANCTIFICATION_REDSTONE_ORE,
            Ores.EBONY_ORE, Ores.SANCTIFICATION_EBONY_ORE,
            Ores.TR_CRIMSON_ORE, Ores.SANCTIFICATION_TR_CRIMSON_ORE,
            Ores.FLESHIFICATION_EBONY_ORE, Ores.FLESHIFICATION_EBONY_ORE,
            Ores.FLESHIFICATION_TR_CRIMSON_ORE, Ores.SANCTIFICATION_TR_CRIMSON_ORE,
            Ores.CORRUPTION_EBONY_ORE, Ores.FLESHIFICATION_EBONY_ORE,
            Ores.CORRUPTION_TR_CRIMSON_ORE, Ores.SANCTIFICATION_TR_CRIMSON_ORE,
            // 植物
            ModBlocks.CORRUPT_GRASS, ModBlocks.HALLOW_GRASS,
            ModBlocks.TR_CRIMSON_GRASS, ModBlocks.HALLOW_GRASS,
            ModBlocks.CRIMSON_THORN, () -> Blocks.AIR,
            ModBlocks.CORRUPTION_THORN, () -> Blocks.AIR,
            ModBlocks.JUNGLE_THORN, () -> Blocks.AIR,
            ModBlocks.PLANTERA_THORN, () -> Blocks.AIR
        ),

        CRIMSON(
            () -> Blocks.DIRT, ModBlocks.TR_CRIMSON_GRASS_BLOCK,
            // 原木
            () -> Blocks.OAK_LOG, ModBlocks.SHADOW_LOG_BLOCKS.LOG,
            () -> Blocks.ACACIA_LOG, ModBlocks.SHADOW_LOG_BLOCKS.LOG,
            () -> Blocks.BIRCH_LOG, ModBlocks.SHADOW_LOG_BLOCKS.LOG,
            () -> Blocks.CHERRY_LOG, ModBlocks.SHADOW_LOG_BLOCKS.LOG,
            () -> Blocks.JUNGLE_LOG, ModBlocks.SHADOW_LOG_BLOCKS.LOG,
            () -> Blocks.DARK_OAK_LOG, ModBlocks.SHADOW_LOG_BLOCKS.LOG,
            () -> Blocks.MANGROVE_LOG, ModBlocks.SHADOW_LOG_BLOCKS.LOG,
            () -> Blocks.SPRUCE_LOG, ModBlocks.SHADOW_LOG_BLOCKS.LOG,
            ModBlocks.PALM_LOG_BLOCKS.LOG, ModBlocks.SHADOW_LOG_BLOCKS.LOG,

            // 树皮
            () -> Blocks.OAK_WOOD, ModBlocks.SHADOW_LOG_BLOCKS.WOOD,
            () -> Blocks.ACACIA_WOOD, ModBlocks.SHADOW_LOG_BLOCKS.WOOD,
            () -> Blocks.BIRCH_WOOD, ModBlocks.SHADOW_LOG_BLOCKS.WOOD,
            () -> Blocks.CHERRY_WOOD, ModBlocks.SHADOW_LOG_BLOCKS.WOOD,
            () -> Blocks.JUNGLE_WOOD, ModBlocks.SHADOW_LOG_BLOCKS.WOOD,
            () -> Blocks.DARK_OAK_WOOD, ModBlocks.SHADOW_LOG_BLOCKS.WOOD,
            () -> Blocks.MANGROVE_WOOD, ModBlocks.SHADOW_LOG_BLOCKS.WOOD,
            () -> Blocks.SPRUCE_WOOD, ModBlocks.SHADOW_LOG_BLOCKS.WOOD,
            ModBlocks.PALM_LOG_BLOCKS.WOOD, ModBlocks.SHADOW_LOG_BLOCKS.WOOD,

            // 去皮原木
            () -> Blocks.STRIPPED_ACACIA_LOG, ModBlocks.SHADOW_LOG_BLOCKS.STRIPPED_LOG,
            () -> Blocks.STRIPPED_CHERRY_LOG, ModBlocks.SHADOW_LOG_BLOCKS.STRIPPED_LOG,
            () -> Blocks.STRIPPED_BIRCH_LOG, ModBlocks.SHADOW_LOG_BLOCKS.STRIPPED_LOG,
            () -> Blocks.STRIPPED_DARK_OAK_LOG, ModBlocks.SHADOW_LOG_BLOCKS.STRIPPED_LOG,
            () -> Blocks.STRIPPED_OAK_LOG, ModBlocks.SHADOW_LOG_BLOCKS.STRIPPED_LOG,
            () -> Blocks.STRIPPED_MANGROVE_LOG, ModBlocks.SHADOW_LOG_BLOCKS.STRIPPED_LOG,
            () -> Blocks.STRIPPED_SPRUCE_LOG, ModBlocks.SHADOW_LOG_BLOCKS.STRIPPED_LOG,
            ModBlocks.PALM_LOG_BLOCKS.STRIPPED_LOG, ModBlocks.SHADOW_LOG_BLOCKS.STRIPPED_LOG,

            // 去皮树皮
            () -> Blocks.STRIPPED_ACACIA_WOOD, ModBlocks.SHADOW_LOG_BLOCKS.STRIPPED_WOOD,
            () -> Blocks.STRIPPED_CHERRY_WOOD, ModBlocks.SHADOW_LOG_BLOCKS.STRIPPED_WOOD,
            () -> Blocks.STRIPPED_BIRCH_WOOD, ModBlocks.SHADOW_LOG_BLOCKS.STRIPPED_WOOD,
            () -> Blocks.STRIPPED_DARK_OAK_WOOD, ModBlocks.SHADOW_LOG_BLOCKS.STRIPPED_WOOD,
            () -> Blocks.STRIPPED_OAK_WOOD, ModBlocks.SHADOW_LOG_BLOCKS.STRIPPED_WOOD,
            () -> Blocks.STRIPPED_MANGROVE_WOOD, ModBlocks.SHADOW_LOG_BLOCKS.STRIPPED_WOOD,
            () -> Blocks.STRIPPED_SPRUCE_WOOD, ModBlocks.SHADOW_LOG_BLOCKS.STRIPPED_WOOD,
            ModBlocks.PALM_LOG_BLOCKS.STRIPPED_WOOD, ModBlocks.SHADOW_LOG_BLOCKS.STRIPPED_WOOD,

            // 树叶
            () -> Blocks.OAK_LEAVES, ModBlocks.SHADOW_LOG_BLOCKS.LEAVES,
            () -> Blocks.ACACIA_LEAVES, ModBlocks.SHADOW_LOG_BLOCKS.LEAVES,
            () -> Blocks.BIRCH_LEAVES, ModBlocks.SHADOW_LOG_BLOCKS.LEAVES,
            () -> Blocks.CHERRY_LEAVES, ModBlocks.SHADOW_LOG_BLOCKS.LEAVES,
            () -> Blocks.JUNGLE_LEAVES, ModBlocks.SHADOW_LOG_BLOCKS.LEAVES,
            () -> Blocks.DARK_OAK_LEAVES, ModBlocks.SHADOW_LOG_BLOCKS.LEAVES,
            () -> Blocks.MANGROVE_LEAVES, ModBlocks.SHADOW_LOG_BLOCKS.LEAVES,
            () -> Blocks.SPRUCE_LEAVES, ModBlocks.SHADOW_LOG_BLOCKS.LEAVES,
            ModBlocks.PALM_LOG_BLOCKS.LEAVES, ModBlocks.SHADOW_LOG_BLOCKS.LEAVES,

            // 原版环境方块
            () -> Blocks.GRASS_BLOCK, ModBlocks.TR_CRIMSON_GRASS_BLOCK,
            () -> Blocks.STONE, ModBlocks.TR_CRIMSON_STONE,
            () -> Blocks.COBBLESTONE, ModBlocks.TR_CRIMSON_COBBLESTONE,
            () -> Blocks.SANDSTONE, ModBlocks.TR_CRIMSON_SANDSTONE,
            () -> Blocks.SAND, ModBlocks.TR_CRIMSON_SAND,
            () -> Blocks.GRASS, ModBlocks.TR_CRIMSON_GRASS,
            () -> Blocks.TALL_GRASS, ModBlocks.TR_CRIMSON_GRASS,
            () -> Blocks.ICE, ModBlocks.RED_ICE,
            () -> Blocks.PACKED_ICE, ModBlocks.RED_PACKED_ICE,
            //矿物
            () -> Blocks.REDSTONE_ORE, ModBlocks.FLESHIFICATION_REDSTONE_ORE,
            () -> Blocks.COAL_ORE, Ores.FLESHIFICATION_COAL_ORE,
            () -> Blocks.LAPIS_ORE, Ores.FLESHIFICATION_LAPIS_ORE,
            () -> Blocks.COPPER_ORE, Ores.FLESHIFICATION_COPPER_ORE,
            () -> Blocks.IRON_ORE, Ores.FLESHIFICATION_IRON_ORE,
            () -> Blocks.EMERALD_ORE, Ores.FLESHIFICATION_EMERALD_ORE,
            () -> Blocks.DIAMOND_ORE, Ores.FLESHIFICATION_DIAMOND_ORE,
            () -> Blocks.GOLD_ORE, Ores.FLESHIFICATION_GOLD_ORE,
            Ores.TIN_ORE, Ores.FLESHIFICATION_TIN_ORE,
            Ores.LEAD_ORE, Ores.FLESHIFICATION_LEAD_ORE,
            Ores.SILVER_ORE, Ores.FLESHIFICATION_SILVER_ORE,
            Ores.TUNGSTEN_ORE, Ores.FLESHIFICATION_TUNGSTEN_ORE,
            Ores.PLATINUM_ORE, Ores.FLESHIFICATION_PLATINUM_ORE,
            Ores.EBONY_ORE, Ores.FLESHIFICATION_EBONY_ORE,
            Ores.TR_CRIMSON_ORE, Ores.FLESHIFICATION_TR_CRIMSON_ORE,

            // 蘑菇
            ModBlocks.LIFE_MUSHROOM, ModBlocks.TR_CRIMSON_MUSHROOM
        ),


        CORRUPT(
            () -> Blocks.DIRT, ModBlocks.CORRUPT_GRASS_BLOCK,
            // 原木
            () -> Blocks.OAK_LOG, ModBlocks.EBONY_LOG_BLOCKS.LOG,
            () -> Blocks.ACACIA_LOG, ModBlocks.EBONY_LOG_BLOCKS.LOG,
            () -> Blocks.BIRCH_LOG, ModBlocks.EBONY_LOG_BLOCKS.LOG,
            () -> Blocks.CHERRY_LOG, ModBlocks.EBONY_LOG_BLOCKS.LOG,
            () -> Blocks.JUNGLE_LOG, ModBlocks.EBONY_LOG_BLOCKS.LOG,
            () -> Blocks.DARK_OAK_LOG, ModBlocks.EBONY_LOG_BLOCKS.LOG,
            () -> Blocks.MANGROVE_LOG, ModBlocks.EBONY_LOG_BLOCKS.LOG,
            () -> Blocks.SPRUCE_LOG, ModBlocks.EBONY_LOG_BLOCKS.LOG,
            ModBlocks.PALM_LOG_BLOCKS.LOG, ModBlocks.EBONY_LOG_BLOCKS.LOG,
            ModBlocks.PEARL_LOG_BLOCKS.LOG, ModBlocks.EBONY_LOG_BLOCKS.LOG,
            // 树皮
            () -> Blocks.OAK_WOOD, ModBlocks.EBONY_LOG_BLOCKS.WOOD,
            () -> Blocks.ACACIA_WOOD, ModBlocks.EBONY_LOG_BLOCKS.WOOD,
            () -> Blocks.BIRCH_WOOD, ModBlocks.EBONY_LOG_BLOCKS.WOOD,
            () -> Blocks.CHERRY_WOOD, ModBlocks.EBONY_LOG_BLOCKS.WOOD,
            () -> Blocks.JUNGLE_WOOD, ModBlocks.EBONY_LOG_BLOCKS.WOOD,
            () -> Blocks.DARK_OAK_WOOD, ModBlocks.EBONY_LOG_BLOCKS.WOOD,
            () -> Blocks.MANGROVE_WOOD, ModBlocks.EBONY_LOG_BLOCKS.WOOD,
            () -> Blocks.SPRUCE_WOOD, ModBlocks.EBONY_LOG_BLOCKS.WOOD,
            ModBlocks.PALM_LOG_BLOCKS.WOOD, ModBlocks.EBONY_LOG_BLOCKS.WOOD,
            ModBlocks.PEARL_LOG_BLOCKS.WOOD, ModBlocks.EBONY_LOG_BLOCKS.WOOD,

            // 去皮原木
            () -> Blocks.STRIPPED_ACACIA_LOG, ModBlocks.EBONY_LOG_BLOCKS.STRIPPED_LOG,
            () -> Blocks.STRIPPED_CHERRY_LOG, ModBlocks.EBONY_LOG_BLOCKS.STRIPPED_LOG,
            () -> Blocks.STRIPPED_BIRCH_LOG, ModBlocks.EBONY_LOG_BLOCKS.STRIPPED_LOG,
            () -> Blocks.STRIPPED_DARK_OAK_LOG, ModBlocks.EBONY_LOG_BLOCKS.STRIPPED_LOG,
            () -> Blocks.STRIPPED_OAK_LOG, ModBlocks.EBONY_LOG_BLOCKS.STRIPPED_LOG,
            () -> Blocks.STRIPPED_MANGROVE_LOG, ModBlocks.EBONY_LOG_BLOCKS.STRIPPED_LOG,
            () -> Blocks.STRIPPED_SPRUCE_LOG, ModBlocks.EBONY_LOG_BLOCKS.STRIPPED_LOG,
            ModBlocks.PALM_LOG_BLOCKS.STRIPPED_LOG, ModBlocks.EBONY_LOG_BLOCKS.STRIPPED_LOG,
            ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_LOG, ModBlocks.EBONY_LOG_BLOCKS.STRIPPED_LOG,

            // 去皮树皮
            () -> Blocks.STRIPPED_ACACIA_WOOD, ModBlocks.EBONY_LOG_BLOCKS.STRIPPED_WOOD,
            () -> Blocks.STRIPPED_CHERRY_WOOD, ModBlocks.EBONY_LOG_BLOCKS.STRIPPED_WOOD,
            () -> Blocks.STRIPPED_BIRCH_WOOD, ModBlocks.EBONY_LOG_BLOCKS.STRIPPED_WOOD,
            () -> Blocks.STRIPPED_DARK_OAK_WOOD, ModBlocks.EBONY_LOG_BLOCKS.STRIPPED_WOOD,
            () -> Blocks.STRIPPED_OAK_WOOD, ModBlocks.EBONY_LOG_BLOCKS.STRIPPED_WOOD,
            () -> Blocks.STRIPPED_MANGROVE_WOOD, ModBlocks.EBONY_LOG_BLOCKS.STRIPPED_WOOD,
            () -> Blocks.STRIPPED_SPRUCE_WOOD, ModBlocks.EBONY_LOG_BLOCKS.STRIPPED_WOOD,
            ModBlocks.PALM_LOG_BLOCKS.STRIPPED_WOOD, ModBlocks.EBONY_LOG_BLOCKS.STRIPPED_WOOD,
            ModBlocks.PEARL_LOG_BLOCKS.STRIPPED_WOOD, ModBlocks.EBONY_LOG_BLOCKS.STRIPPED_WOOD,
            // 树叶
            () -> Blocks.OAK_LEAVES, ModBlocks.EBONY_LOG_BLOCKS.LEAVES,
            () -> Blocks.ACACIA_LEAVES, ModBlocks.EBONY_LOG_BLOCKS.LEAVES,
            () -> Blocks.BIRCH_LEAVES, ModBlocks.EBONY_LOG_BLOCKS.LEAVES,
            () -> Blocks.CHERRY_LEAVES, ModBlocks.EBONY_LOG_BLOCKS.LEAVES,
            () -> Blocks.JUNGLE_LEAVES, ModBlocks.EBONY_LOG_BLOCKS.LEAVES,
            () -> Blocks.DARK_OAK_LEAVES, ModBlocks.EBONY_LOG_BLOCKS.LEAVES,
            () -> Blocks.MANGROVE_LEAVES, ModBlocks.EBONY_LOG_BLOCKS.LEAVES,
            () -> Blocks.SPRUCE_LEAVES, ModBlocks.EBONY_LOG_BLOCKS.LEAVES,
            ModBlocks.PALM_LOG_BLOCKS.LEAVES, ModBlocks.EBONY_LOG_BLOCKS.LEAVES,
            ModBlocks.PEARL_LOG_BLOCKS.LEAVES, ModBlocks.EBONY_LOG_BLOCKS.LEAVES,


            // 原版环境方块
            () -> Blocks.GRASS_BLOCK, ModBlocks.CORRUPT_GRASS_BLOCK,
            () -> Blocks.STONE, ModBlocks.EBONY_STONE,
            () -> Blocks.COBBLESTONE, ModBlocks.EBONY_COBBLESTONE,
            () -> Blocks.SANDSTONE, ModBlocks.EBONY_SANDSTONE,
            () -> Blocks.SAND, ModBlocks.EBONY_SAND,
            () -> Blocks.GRASS, ModBlocks.CORRUPT_GRASS,
            () -> Blocks.TALL_GRASS, ModBlocks.CORRUPT_GRASS,
            () -> Blocks.ICE, ModBlocks.PURPLE_ICE,
            () -> Blocks.PACKED_ICE, ModBlocks.PURPLE_PACKED_ICE,
            //矿物
            () -> Blocks.REDSTONE_ORE, ModBlocks.CORRUPTION_REDSTONE_ORE,
            () -> Blocks.COAL_ORE, Ores.CORRUPTION_COAL_ORE,
            () -> Blocks.LAPIS_ORE, Ores.CORRUPTION_LAPIS_ORE,
            () -> Blocks.COPPER_ORE, Ores.CORRUPTION_COPPER_ORE,
            () -> Blocks.IRON_ORE, Ores.CORRUPTION_IRON_ORE,
            () -> Blocks.EMERALD_ORE, Ores.CORRUPTION_EMERALD_ORE,
            () -> Blocks.DIAMOND_ORE, Ores.CORRUPTION_DIAMOND_ORE,
            () -> Blocks.GOLD_ORE, Ores.CORRUPTION_GOLD_ORE,
            Ores.TIN_ORE, Ores.CORRUPTION_TIN_ORE,
            Ores.LEAD_ORE, Ores.CORRUPTION_LEAD_ORE,
            Ores.SILVER_ORE, Ores.CORRUPTION_SILVER_ORE,
            Ores.TUNGSTEN_ORE, Ores.CORRUPTION_TUNGSTEN_ORE,
            Ores.PLATINUM_ORE, Ores.CORRUPTION_PLATINUM_ORE,
            Ores.EBONY_ORE, Ores.CORRUPTION_EBONY_ORE,
            Ores.TR_CRIMSON_ORE, Ores.CORRUPTION_TR_CRIMSON_ORE,

            Ores.SANCTIFICATION_TIN_ORE, Ores.CORRUPTION_TIN_ORE,
            Ores.SANCTIFICATION_LEAD_ORE, Ores.CORRUPTION_LEAD_ORE,
            Ores.SANCTIFICATION_SILVER_ORE, Ores.CORRUPTION_SILVER_ORE,
            Ores.SANCTIFICATION_TUNGSTEN_ORE, Ores.CORRUPTION_TUNGSTEN_ORE,
            Ores.SANCTIFICATION_PLATINUM_ORE, Ores.CORRUPTION_PLATINUM_ORE,
            Ores.SANCTIFICATION_COAL_ORE, Ores.CORRUPTION_COAL_ORE,
            Ores.SANCTIFICATION_COPPER_ORE, Ores.CORRUPTION_COPPER_ORE,
            Ores.SANCTIFICATION_IRON_ORE, Ores.CORRUPTION_IRON_ORE,
            Ores.SANCTIFICATION_GOLD_ORE, Ores.CORRUPTION_GOLD_ORE,
            Ores.SANCTIFICATION_DIAMOND_ORE, Ores.CORRUPTION_DIAMOND_ORE,
            ModBlocks.SANCTIFICATION_REDSTONE_ORE, ModBlocks.CORRUPTION_REDSTONE_ORE,

            // 邪恶环境方块
            ModBlocks.HALLOW_GRASS_BLOCK, ModBlocks.CORRUPT_GRASS_BLOCK,

            ModBlocks.PEARL_STONE, ModBlocks.EBONY_STONE,

            ModBlocks.PEARL_COBBLESTONE, ModBlocks.EBONY_COBBLESTONE,

            ModBlocks.HARDENED_SAND_BLOCK, ModBlocks.EBONY_HARDENED_SAND_BLOCK,
            ModBlocks.PEARL_HARDENED_SAND_BLOCK, ModBlocks.EBONY_HARDENED_SAND_BLOCK,

            ModBlocks.PEARL_SANDSTONE, ModBlocks.EBONY_SANDSTONE,

            ModBlocks.PINK_ICE, ModBlocks.PURPLE_ICE,
            ModBlocks.PINK_PACKED_ICE, ModBlocks.PURPLE_PACKED_ICE,

            // 蘑菇
            ModBlocks.LIFE_MUSHROOM, ModBlocks.EBONY_MUSHROOM,

            // 植物
            ModBlocks.HALLOW_GRASS, ModBlocks.CORRUPT_GRASS,
            ModBlocks.CRIMSON_THORN, ModBlocks.CORRUPTION_THORN,
            ModBlocks.JUNGLE_THORN, ModBlocks.CORRUPTION_THORN,
            ModBlocks.PLANTERA_THORN, ModBlocks.CORRUPTION_THORN
        ),

        GLOWING(
            () -> Blocks.MUD, ModBlocks.MUSHROOM_GRASS_BLOCK,
            ModBlocks.JUNGLE_SPORE, ModBlocks.GLOWING_MUSHROOM
        ),

        PURE(
            ModBlocks.ASH_BLOCK, ModBlocks.ASH_GRASS_BLOCK,
            () -> Blocks.MUD, ModBlocks.MUSHROOM_GRASS_BLOCK,
            ModBlocks.TR_CRIMSON_MUSHROOM, ModBlocks.LIFE_MUSHROOM,
            ModBlocks.EBONY_MUSHROOM, ModBlocks.LIFE_MUSHROOM,
            ModBlocks.CORRUPT_GRASS, () -> Blocks.GRASS,
            ModBlocks.TR_CRIMSON_GRASS, () -> Blocks.GRASS,
            ModBlocks.HALLOW_GRASS, () -> Blocks.GRASS,
            ModBlocks.TR_CRIMSON_STONE, () -> Blocks.STONE,
            ModBlocks.TR_CRIMSON_SAND, () -> Blocks.SAND,
            ModBlocks.TR_CRIMSON_GRASS_BLOCK, () -> Blocks.GRASS_BLOCK,
            ModBlocks.CORRUPT_GRASS_BLOCK, () -> Blocks.GRASS_BLOCK,
            ModBlocks.HALLOW_GRASS_BLOCK, () -> Blocks.GRASS_BLOCK,
            ModBlocks.CRIMSON_THORN, () -> Blocks.AIR,
            ModBlocks.CORRUPTION_THORN, () -> Blocks.AIR
        );

        private Map<Supplier<? extends Block>, Supplier<? extends Block>> supplierMap;
        private Map<Block, Block> blockMap;

        @SafeVarargs
        Type(Supplier<? extends Block>... suppliers) {
            if (suppliers.length % 2 != 0) throw new RuntimeException("Not enough suppliers!");
            Hashtable<Supplier<? extends Block>, Supplier<? extends Block>> map = new Hashtable<>();
            for (int i = 0; i < suppliers.length / 2; i++) {
                int j = i * 2;
                map.put(suppliers[j], suppliers[j + 1]);
            }
            this.supplierMap = map;
        }

        public Map<Block, Block> getBlockMap() {
            return blockMap;
        }

        public static void buildMap() {
            for (Type type : values()) {
                ImmutableMap.Builder<Block, Block> map = ImmutableMap.builder();
                type.supplierMap.forEach((s1, s2) -> map.put(s1.get(), s2.get()));
                type.blockMap = map.build();
                type.supplierMap = null;
            }
        }
    }
}
