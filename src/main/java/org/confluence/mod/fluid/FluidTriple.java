package org.confluence.mod.fluid;

import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.RegistryObject;

public record FluidTriple(RegistryObject<FluidType> fluidType, RegistryObject<FlowingFluid> fluid,
                          RegistryObject<FlowingFluid> flowingFluid) {
}
