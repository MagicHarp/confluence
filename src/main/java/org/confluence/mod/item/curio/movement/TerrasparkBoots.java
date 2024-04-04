package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.confluence.mod.item.curio.ILavaImmune;
import org.confluence.mod.item.curio.combat.IFireImmune;
import org.confluence.mod.item.curio.combat.ILavaHurtReduce;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TerrasparkBoots extends FrostSparkBoots implements IFireImmune, ILavaImmune, ILavaHurtReduce, IFluidWalk {
    @Override
    public boolean canStandOn(FluidState fluidState) {
        return fluidState.is(Fluids.WATER) || fluidState.is(Fluids.LAVA);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
    }
}
