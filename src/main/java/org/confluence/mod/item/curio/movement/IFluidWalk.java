package org.confluence.mod.item.curio.movement;

import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public interface IFluidWalk {
    default boolean canStandOn(FluidState fluidState) {
        return fluidState.is(Fluids.WATER);
    }
}
