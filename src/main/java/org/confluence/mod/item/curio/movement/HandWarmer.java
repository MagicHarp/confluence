package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class HandWarmer extends BaseCurioItem {
    public HandWarmer() {
        super(ModRarity.GREEN);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.hand_warmer.info"),
            Component.translatable("item.confluence.hand_warmer.info2")
        };
    }
}
