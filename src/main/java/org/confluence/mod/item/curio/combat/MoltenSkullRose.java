package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.curio.BaseCurioItem;

public class MoltenSkullRose extends BaseCurioItem implements IFireImmune, ILavaImmune {
    @Override
    public int getLavaImmuneTicks() {
        return 140;
    }
}
