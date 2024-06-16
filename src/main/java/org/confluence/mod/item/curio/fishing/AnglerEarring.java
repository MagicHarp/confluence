package org.confluence.mod.item.curio.fishing;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;

public class AnglerEarring extends BaseCurioItem implements IFishingPower {
    @Override
    public float getFishingBonus() {
        return 10.0F;
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.angler_earring.info")
        };
    }
}
