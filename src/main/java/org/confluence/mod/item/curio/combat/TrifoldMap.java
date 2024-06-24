package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class TrifoldMap extends BaseCurioItem implements EffectInvul.Nausea {
    public TrifoldMap() {
        super(ModRarity.LIGHT_RED);
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.trifold_map.info"),
            Component.translatable("item.confluence.trifold_map.info2")
        };
    }
}
