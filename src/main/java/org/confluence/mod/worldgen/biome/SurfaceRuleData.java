package org.confluence.mod.worldgen.biome;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.confluence.mod.block.ModBlocks;

public class SurfaceRuleData
{
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource RED_TERRACOTTA = makeStateRule(Blocks.RED_TERRACOTTA);
    private static final SurfaceRules.RuleSource BLUE_TERRACOTTA = makeStateRule(Blocks.BLUE_TERRACOTTA);

    private static final SurfaceRules.RuleSource CORRUPT_GRASS_BLOCK = makeStateRule(ModBlocks.CORRUPT_GRASS_BLOCK.get());
//将方块转化为可用？
    public static SurfaceRules.RuleSource makeRules()
    {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);
        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);
        SurfaceRules.RuleSource corrupt_grass_blocksurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel,CORRUPT_GRASS_BLOCK), DIRT);
//不知为何，无效应用
        return SurfaceRules.sequence(
            SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.HOT_RED), RED_TERRACOTTA),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.COLD_BLUE), BLUE_TERRACOTTA),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.THE_CORRUPTION), CORRUPT_GRASS_BLOCK),

            // Default to a grass and dirt surface
            SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface)
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block)
    {
        return SurfaceRules.state(block.defaultBlockState());
    }
}