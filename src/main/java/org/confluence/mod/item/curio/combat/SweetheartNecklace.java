package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.misc.ModRarity;

public class SweetheartNecklace extends PanicNecklace implements IHoneycomb {
    public SweetheartNecklace() {
        super(ModRarity.ORANGE);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.sweetheart_necklace.info")
        };
    }
}
