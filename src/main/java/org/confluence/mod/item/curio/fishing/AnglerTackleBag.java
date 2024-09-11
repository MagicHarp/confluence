package org.confluence.mod.item.curio.fishing;

import net.minecraft.world.item.Rarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class AnglerTackleBag extends BaseCurioItem implements IHighTestFishingLine, ITackleBox, IFishingPower {
    public AnglerTackleBag() {
        super(ModRarity.ORANGE);
    }

    public AnglerTackleBag(Rarity rarity) {
        super(rarity);
    }

    @Override
    public float getFishingBonus() {
        return 10.0F;
    }
}
