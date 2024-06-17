package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class CrossNecklace extends BaseCurioItem implements IInvulnerableTime {
    public CrossNecklace() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.cross_necklace.info"),
            Component.translatable("item.confluence.cross_necklace.info2")
        };
    }
}
