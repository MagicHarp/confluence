package org.confluence.mod.entity.demoneye;

import net.minecraft.world.entity.ai.goal.Goal;

public class DemonEyeSweepAttackGoal extends Goal {
    private final DemonEye self;

    DemonEyeSweepAttackGoal(DemonEye demonEye) {
        this.self = demonEye;
    }

    public boolean canUse() {
        return self.getTarget() != null && self.attackPhase == DemonEye.AttackPhase.SWOOP;
    }
}
