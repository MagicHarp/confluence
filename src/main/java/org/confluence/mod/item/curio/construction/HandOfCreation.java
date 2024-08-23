package org.confluence.mod.item.curio.construction;

import org.confluence.mod.item.IRangePickup;
import org.confluence.mod.item.curio.movement.StepStool;
import org.confluence.mod.misc.ModRarity;

public class HandOfCreation extends StepStool implements IRightClickSubtractor, IBreakSpeedBonus, IRangePickup {
    public HandOfCreation() {
        super(ModRarity.LIGHT_PURPLE);
    }

    @Override
    public float getBreakBonus() {
        return 0.25F;
    }
}
