package org.confluence.mod.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import static org.confluence.mod.Confluence.MODID;

public final class ModWorldGens {
    public static final ResourceKey<WorldPreset> CORRUPTION = ResourceKey.create(Registries.WORLD_PRESET, new ResourceLocation(MODID, "corruption"));
    public static final ResourceKey<WorldPreset> CRIMSON = ResourceKey.create(Registries.WORLD_PRESET, new ResourceLocation(MODID, "crimson"));

    public static final RegistryObject<Codec<ChunkGenerator>> BANNED_BIOME = RegistryObject.create(new ResourceLocation(MODID, "banned_biome"), BuiltInRegistries.CHUNK_GENERATOR.key(), MODID);

    public static void registerGenerators(RegisterEvent event) {
        event.register(BuiltInRegistries.CHUNK_GENERATOR.key(), helper -> {
            helper.register(BANNED_BIOME.getId(), BannedBiomeNoiseBasedChunkGenerator.CODEC);
        });
    }
}
