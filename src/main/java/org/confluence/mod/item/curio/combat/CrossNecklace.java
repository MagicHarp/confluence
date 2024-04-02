package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.curio.BaseCurioItem;

public class CrossNecklace extends BaseCurioItem implements IInvulnerableTime {
    @Override
    public int getTime() {
        return 40;
    }
}
