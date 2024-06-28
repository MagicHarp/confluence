package org.confluence.mod.worldgen.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.VanillaParameterOverlayBuilder;

import java.util.function.Consumer;

import static terrablender.api.ParameterUtils.*;

public class GlowingMushroomRegion extends Region {
    public GlowingMushroomRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();
        new ParameterPointListBuilder()
            .temperature(Temperature.FULL_RANGE)
            .humidity(Humidity.FULL_RANGE)
            .continentalness(Continentalness.INLAND)
            .erosion(Erosion.EROSION_4, Erosion.EROSION_6)
            .depth(Climate.Parameter.span(0.75f,1.5f))
            .weirdness(Weirdness.MID_SLICE_VARIANT_ASCENDING)
            .build().forEach(point -> builder.add(point, ModBiomes.GLOWING_MUSHROOM));

        // Add our points to the mapper
        builder.build().forEach(mapper);
    }
}