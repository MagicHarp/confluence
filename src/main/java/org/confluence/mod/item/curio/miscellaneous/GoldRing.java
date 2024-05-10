package org.confluence.mod.item.curio.miscellaneous;

import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.IRangePickup;

public class GoldRing extends BaseCurioItem implements IRangePickup.Coin {
    public GoldRing() {
        super(ModRarity.PINK);
    }
}
