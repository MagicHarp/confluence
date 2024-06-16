package org.confluence.mod.item.curio.miscellaneous;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.IRangePickup;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class GoldRing extends BaseCurioItem implements IRangePickup.Coin {
    public GoldRing() {
        super(ModRarity.PINK);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.gold_ring.info"),
                Component.translatable("item.confluence.gold_ring.info2"),
                Component.translatable("item.confluence.gold_ring.info3"),
                Component.translatable("item.confluence.gold_ring.info4"),
                Component.translatable("item.confluence.gold_ring.info5")
        };
    }
}
