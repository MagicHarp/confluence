package org.confluence.mod.item.curio.HealthAndMana;

import net.minecraft.network.chat.Component;
import org.confluence.mod.datagen.limit.CustomName;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class NaturesGift extends BaseCurioItem implements IManaReduce, CustomName {
    public NaturesGift() {
        super(ModRarity.ORANGE);
    }

    @Override
    public double getManaReduce() {
        return 0.06;
    }

    @Override
    public String getGenName() {
        return "Nature's Gift";
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.natures_gift.info")
        };
    }
}
