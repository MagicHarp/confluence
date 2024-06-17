package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class FastClock extends BaseCurioItem implements EffectInvul.Slowness {
    public FastClock() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.fast_clock.info"),
            Component.translatable("item.confluence.fast_clock.info2"),
            Component.translatable("item.confluence.fast_clock.info3")
        };
    }
}
