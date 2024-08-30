package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class HolyWater extends BaseCurioItem implements EffectInvul.Wither {
    public HolyWater() {
        super(ModRarity.LIGHT_RED);
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.holy_water.info")
        };
    }
}
