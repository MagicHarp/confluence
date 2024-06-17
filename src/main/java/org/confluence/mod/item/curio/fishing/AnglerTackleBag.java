package org.confluence.mod.item.curio.fishing;

import net.minecraft.network.chat.Component;
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

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.angler_tackle_bag.info"),
            Component.translatable("item.confluence.angler_tackle_bag.info2"),
            Component.translatable("item.confluence.angler_tackle_bag.info3"),
            Component.translatable("item.confluence.angler_tackle_bag.info4")
        };
    }
}
