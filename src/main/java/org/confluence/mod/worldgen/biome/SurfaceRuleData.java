package org.confluence.mod.worldgen.biome;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.confluence.mod.block.ModBlocks;

public class SurfaceRuleData {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);

    private static final SurfaceRules.RuleSource RED_TERRACOTTA = makeStateRule(Blocks.RED_TERRACOTTA);
    private static final SurfaceRules.RuleSource BLUE_TERRACOTTA = makeStateRule(Blocks.BLUE_TERRACOTTA);
    private static final SurfaceRules.RuleSource CORRUPTED_GRASS_BLOCK = makeStateRule(ModBlocks.CORRUPT_GRASS_BLOCK.get());

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);
        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);

        return SurfaceRules.sequence(
            SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.HOT_RED), RED_TERRACOTTA),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.COLD_BLUE), BLUE_TERRACOTTA),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.THE_CORRUPTION), CORRUPTED_GRASS_BLOCK),

            SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface)
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}