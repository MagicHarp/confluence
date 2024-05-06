package org.confluence.mod.worldgen.biome;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import org.confluence.mod.Confluence;

public class ModBiomes {
    public static final ResourceKey<Biome> THE_CORRUPTION = register("the_corruption");
    public static final ResourceKey<Biome> ANOTHER_CRIMSON = register("another_crimson");
    public static final ResourceKey<Biome> THE_HALLOW = register("the_hallow");

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(Confluence.MODID, name));
    }
}