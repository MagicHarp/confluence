package org.confluence.mod.item.curio.miscellaneous;

import org.confluence.mod.item.IRangePickup;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;

public class GoldRing extends BaseCurioItem implements IRangePickup.Coin {
    public GoldRing() {
        super(ModRarity.PINK);
    }
}
