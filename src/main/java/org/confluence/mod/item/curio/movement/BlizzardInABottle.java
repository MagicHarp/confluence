package org.confluence.mod.item.curio.movement;

import org.confluence.mod.item.curio.BaseCurioItem;

public class BlizzardInABottle extends BaseCurioItem implements IMultiJump {
    @Override
    public int getJumpTimes() {
        return 0;
    }

    @Override
    public double getMultiY() {
        return 0;
    }
}
