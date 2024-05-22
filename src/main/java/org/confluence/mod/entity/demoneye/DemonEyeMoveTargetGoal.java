package org.confluence.mod.entity.demoneye;

import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public abstract class DemonEyeMoveTargetGoal extends Goal {
    protected final DemonEye self;

    public DemonEyeMoveTargetGoal(DemonEye demonEye) {
        setFlags(EnumSet.of(Flag.MOVE));
        this.self = demonEye;
    }

    protected boolean touchingTarget() {
        return self.moveTargetPoint.distanceToSqr(self.getX(), self.getY(), self.getZ()) < 4.0;
    }
}
