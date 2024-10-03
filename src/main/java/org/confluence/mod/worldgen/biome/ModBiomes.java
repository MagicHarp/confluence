package org.confluence.mod.worldgen.biome;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import org.confluence.mod.Confluence;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

import static org.confluence.mod.Confluence.MODID;

public final class ModBiomes {
    public static final ResourceKey<Biome> THE_CORRUPTION = register("the_corruption");
    public static final ResourceKey<Biome> TR_CRIMSON = register("tr_crimson");
    public static final ResourceKey<Biome> THE_HALLOW = register("the_hallow");
    public static final ResourceKey<Biome> ASH_FOREST = register("ash_forest");
    public static final ResourceKey<Biome> ASH_WASTELAND = register("ash_wasteland");
    public static final ResourceKey<Biome> GLOWING_MUSHROOM = register("glowing_mushroom");

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, Confluence.asResource(name));
    }

    public static void registerRegionAndSurface() {
        Regions.register(new AnotherCrimsonRegion(Confluence.asResource("tr_crimson"), 1));
        Regions.register(new TheCorruptionRegion(Confluence.asResource("the_corruption"), 1));
        Regions.register(new AshForestRegion(Confluence.asResource("ash_forest"), 1));
        Regions.register(new AshWastelandRegion(Confluence.asResource("ash_wasteland"), 1));
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MODID, SurfaceRuleData.makeRules());
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.NETHER, MODID, SurfaceRuleData.makeRules());
    }
}