package org.confluence.mod.item.curio.miscellaneous;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.IRangePickup;
import org.confluence.mod.item.curio.BaseCurioItem;

public class TreasureMagnet extends BaseCurioItem implements IRangePickup.Drops {

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.treasure_magnet.info"),
            Component.translatable("item.confluence.treasure_magnet.info2"),
            Component.translatable("item.confluence.treasure_magnet.info3")
        };
    }
}
