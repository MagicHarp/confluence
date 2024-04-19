package org.confluence.mod.worldgen.biome;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
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
    public static SurfaceRules.RuleSource build(){
        return SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.THE_CORRUPTION),//如果群系为我们之前创建的自定义群系
            SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.hole()),//且如果位置不是矿洞内
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, true, 3, CaveSurface.FLOOR),makeStateRule(Blocks.DIRT)),//将距离表面3格以内的方块替换为泥土
                    SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, true, 5, CaveSurface.FLOOR),makeStateRule(Blocks.COARSE_DIRT))//对于不符合上方“距离表面3格以内”不成立的方块，如果距离表面五格以内，则替换为砂土
                )));
    }

    private static SurfaceRules.RuleSource preliminarySurfaceRule(ResourceKey<Biome> biomeKey, BlockState groundBlock, BlockState undergroundBlock, BlockState underwaterBlock) {
    return SurfaceRules.ifTrue(SurfaceRules.isBiome(biomeKey),
        SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(),
            SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
                    SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), SurfaceRules.state(groundBlock)), SurfaceRules.state(underwaterBlock))),
                SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, true, 0, CaveSurface.FLOOR), SurfaceRules.state(undergroundBlock)))));
}
    private static SurfaceRules.RuleSource makeStateRule(Block block)
    {
        return SurfaceRules.state(block.defaultBlockState());
    }
}