package org.confluence.mod.item.curio.movement;

import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.ILavaImmune;
import org.confluence.mod.item.curio.combat.IFireImmune;
import org.confluence.mod.item.curio.combat.ILavaHurtReduce;

public class LavaWaders extends BaseCurioItem implements IFireImmune, ILavaImmune, ILavaHurtReduce, IFluidWalk {
    public LavaWaders() {
        super(ModRarity.LIME);
    }

    @Override
    public boolean canStandOn(FluidState fluidState) {
        return fluidState.is(Fluids.WATER) || fluidState.is(Fluids.LAVA);
    }
}
