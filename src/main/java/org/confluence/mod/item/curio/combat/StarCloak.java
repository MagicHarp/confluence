package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class StarCloak extends BaseCurioItem implements IStarCloak {
    public StarCloak() {
        super(ModRarity.LIGHT_RED);
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.star_cloak.info"),
            Component.translatable("item.confluence.star_cloak.info2")
        };
    }
}
