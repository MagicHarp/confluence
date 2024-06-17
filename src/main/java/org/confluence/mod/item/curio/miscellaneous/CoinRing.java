package org.confluence.mod.item.curio.miscellaneous;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.IRangePickup;

public class CoinRing extends LuckyCoin implements IRangePickup.Coin {
    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.coin_ring.info"),
            Component.translatable("item.confluence.coin_ring.info2")
        };
    }
}
