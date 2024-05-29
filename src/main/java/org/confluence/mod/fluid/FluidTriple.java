package org.confluence.mod.fluid;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.RegistryObject;

public record FluidTriple(RegistryObject<FluidType> fluidType, RegistryObject<FlowingFluid> fluid, RegistryObject<FlowingFluid> flowingFluid) {
    public static FluidBuilder builder(ResourceLocation location) {
        FluidBuilder builder = new FluidBuilder(location);
        FluidBuilder.BUILDERS.put(location, builder);
        return builder;
    }
}
