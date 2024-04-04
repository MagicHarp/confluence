package org.confluence.mod.item.curio.movement;

import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.ILavaImmune;
import org.confluence.mod.item.curio.combat.IFireAttack;
import org.confluence.mod.item.curio.combat.IFireImmune;

public class MagmaSkull extends BaseCurioItem implements IFireImmune, IFireAttack, ILavaImmune {
    @Override
    public int getLavaImmuneTicks() {
        return 140;
    }
}
