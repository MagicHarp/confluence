package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class StarCloak extends BaseCurioItem implements IStarCloak {
    public StarCloak() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.star_cloak.info"),
            Component.translatable("item.confluence.star_cloak.info2"),
            Component.translatable("item.confluence.star_cloak.info3"),
            Component.translatable("item.confluence.star_cloak.info4"),
            Component.translatable("item.confluence.star_cloak.info5")
        };
    }
}
