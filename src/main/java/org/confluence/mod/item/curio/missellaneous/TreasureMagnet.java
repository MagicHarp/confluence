package org.confluence.mod.item.curio.missellaneous;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.IRangePickup;
import org.confluence.mod.item.curio.BaseCurioItem;

public class TreasureMagnet extends BaseCurioItem implements IRangePickup.Drops {

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.treasure_magnet.info")
        };
    }
}
