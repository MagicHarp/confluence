package org.confluence.mod.worldgen.biome;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import org.confluence.mod.block.ModBlocks;

public class SurfaceRuleData {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource CORRUPTED_GRASS_BLOCK = makeStateRule(ModBlocks.CORRUPT_GRASS_BLOCK.get());
    private static final SurfaceRules.RuleSource EBONY_STONE = makeStateRule(ModBlocks.EBONY_STONE.get());
    private static final SurfaceRules.RuleSource HALLOW_GRASS_BLOCK = makeStateRule(ModBlocks.HALLOW_GRASS_BLOCK.get());
    private static final SurfaceRules.RuleSource ANOTHER_CRIMSON_GRASS_BLOCK = makeStateRule(ModBlocks.ANOTHER_CRIMSON_GRASS_BLOCK.get());

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);
        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);
        SurfaceRules.RuleSource corrupt_grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, CORRUPTED_GRASS_BLOCK), DIRT);
        SurfaceRules.RuleSource hallow_grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, HALLOW_GRASS_BLOCK), DIRT);
        SurfaceRules.RuleSource another_crimson_grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, ANOTHER_CRIMSON_GRASS_BLOCK), DIRT);
        SurfaceRules.RuleSource corrupt_stone_Surface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, EBONY_STONE), EBONY_STONE);
        return SurfaceRules.sequence(
        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.THE_CORRUPTION),
            SurfaceRules.sequence(
                    SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, corrupt_grassSurface))),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.THE_CORRUPTION),
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, corrupt_stone_Surface))),
        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.THE_HALLOW),
            SurfaceRules.sequence(
                    SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, hallow_grassSurface))),
        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.ANOTHER_CRIMSON),
            SurfaceRules.sequence(
                    SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, another_crimson_grassSurface))),

            SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface)
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}