package org.confluence.mod.item.curio.fishing;

import org.confluence.mod.item.curio.BaseCurioItem;

public class FishingBobber extends BaseCurioItem implements IFishingPower {
    @Override
    public float getFishingBonus() {
        return 10.0F;
    }
}
