package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.material.FluidState;
import org.confluence.mod.misc.ModTags;

public interface IFluidWalk {
    default boolean canStandOn(FluidState fluidState) {
        return fluidState.is(ModTags.NOT_LAVA);
    }

    Component NOT_LAVA = Component.translatable("curios.tooltip.fluid_walk.part");

    Component ALL_FLUID = Component.translatable("curios.tooltip.fluid_walk.all");
}
