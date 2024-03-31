package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.curio.BaseCurioItem;

public class LavaCharm extends BaseCurioItem implements ILavaImmune {
    @Override
    public int getLavaImmuneTicks() {
        return 140;
    }
}
