package org.confluence.mod.item.curio.movement;

import org.confluence.mod.item.curio.BaseCurioItem;

public class RocketBoots extends BaseCurioItem implements IMayFly {
    @Override
    public int getFlyTicks() {
        return 32;
    }

    @Override
    public double getFlySpeed() {
        return 10.0D;
    }
}
