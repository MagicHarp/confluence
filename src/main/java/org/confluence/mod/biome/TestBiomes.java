package org.confluence.mod.biome;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import org.confluence.mod.Confluence;

public class TestBiomes
{
    public static final ResourceKey<Biome> HOT_RED = register("hot_red");
    public static final ResourceKey<Biome> COLD_BLUE = register("cold_blue");

    private static ResourceKey<Biome> register(String name)
    {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(Confluence.MODID, name));
    }
}