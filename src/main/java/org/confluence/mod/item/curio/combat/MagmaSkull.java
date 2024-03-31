package org.confluence.mod.item.curio.combat;

import org.confluence.mod.item.curio.BaseCurioItem;

public class MagmaSkull extends BaseCurioItem implements IFireImmune, IFireAttack, ILavaImmune {
    @Override
    public int getLavaImmuneTicks() {
        return 140;
    }
}
