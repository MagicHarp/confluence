package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class Searchlight extends BaseCurioItem implements EffectInvul.Blindness, EffectInvul.Darkness {
    public Searchlight() {
        super(ModRarity.PINK);
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.searchlight.info"),
            Component.translatable("item.confluence.searchlight.info2")
        };
    }
}
