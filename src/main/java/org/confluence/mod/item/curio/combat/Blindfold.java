package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class Blindfold extends BaseCurioItem implements EffectInvul.Blindness {
    public Blindfold() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.blindfold.info"),
            Component.translatable("item.confluence.blindfold.info2"),
            Component.translatable("item.confluence.blindfold.info3")
        };
    }
}
