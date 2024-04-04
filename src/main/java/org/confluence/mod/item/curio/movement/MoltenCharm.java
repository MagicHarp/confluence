package org.confluence.mod.item.curio.movement;

import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.ILavaImmune;
import org.confluence.mod.item.curio.combat.IFireImmune;

public class MoltenCharm extends BaseCurioItem implements IFireImmune, ILavaImmune {
    public MoltenCharm() {
        super(ModRarity.PINK);
    }
}
