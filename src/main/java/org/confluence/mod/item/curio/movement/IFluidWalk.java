package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.confluence.mod.fluid.ModFluids;

public interface IFluidWalk {
    default boolean canStandOn(FluidState fluidState) {
        return fluidState.is(Fluids.WATER) || fluidState.is(ModFluids.HONEY.fluid.get());
    }

    Component WATER_HONEY = Component.translatable("curios.tooltip.fluid_walk.part");

    Component ALL_FLUID = Component.translatable("curios.tooltip.fluid_walk.all");
}
