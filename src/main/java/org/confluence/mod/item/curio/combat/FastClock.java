package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class FastClock extends BaseCurioItem implements EffectInvul.Slowness {
    public FastClock() {
        super(ModRarity.LIGHT_RED);
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.fast_clock.info")
        };
    }
}
