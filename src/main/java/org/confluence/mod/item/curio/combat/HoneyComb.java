package org.confluence.mod.item.curio.combat;

import net.minecraft.world.item.Rarity;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;

public class HoneyComb extends BaseCurioItem implements IHoneycomb {
    public HoneyComb() {
        super(ModRarity.GREEN);
    }

    public HoneyComb(Rarity rarity) {
        super(rarity);
    }
}
