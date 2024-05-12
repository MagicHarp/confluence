package org.confluence.mod.worldgen.biome;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.confluence.mod.block.ModBlocks;

public class SurfaceRuleData {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource SAND = makeStateRule(Blocks.SAND);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource CORRUPT_GRASS_BLOCK = makeStateRule(ModBlocks.CORRUPT_GRASS_BLOCK.get());
    private static final SurfaceRules.RuleSource EBONY_STONE = makeStateRule(ModBlocks.EBONY_STONE.get());
    private static final SurfaceRules.RuleSource HALLOW_GRASS_BLOCK = makeStateRule(ModBlocks.HALLOW_GRASS_BLOCK.get());
    private static final SurfaceRules.RuleSource ANOTHER_CRIMSON_GRASS_BLOCK = makeStateRule(ModBlocks.ANOTHER_CRIMSON_GRASS_BLOCK.get());

    //地狱
    private static final SurfaceRules.RuleSource ASH_BLOCK = makeStateRule(ModBlocks.ASH_BLOCK.get());

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);
        SurfaceRules.ConditionSource isUnderWaterLevel = SurfaceRules.waterStartCheck(-6, -1);
        SurfaceRules.ConditionSource isHole = SurfaceRules.hole();
        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);
        SurfaceRules.RuleSource underwaterSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isUnderWaterLevel, SAND), SAND);
        SurfaceRules.RuleSource corruptGrassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, CORRUPT_GRASS_BLOCK), DIRT);
        SurfaceRules.RuleSource hallowGrassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, HALLOW_GRASS_BLOCK), DIRT);
        SurfaceRules.RuleSource anotherCrimsonGrassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, ANOTHER_CRIMSON_GRASS_BLOCK), DIRT);
        SurfaceRules.RuleSource corruptStoneSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, EBONY_STONE), EBONY_STONE);
        SurfaceRules.RuleSource ashSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, ASH_BLOCK), ASH_BLOCK);
        return SurfaceRules.sequence(
            SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.THE_CORRUPTION),
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, corruptGrassSurface))),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.THE_HALLOW),
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, hallowGrassSurface))),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.ANOTHER_CRIMSON),
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, anotherCrimsonGrassSurface))),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.ASH_FOREST),
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, ashSurface))),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.ASH_WASTELAND),
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, ashSurface))),

            SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface)
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}