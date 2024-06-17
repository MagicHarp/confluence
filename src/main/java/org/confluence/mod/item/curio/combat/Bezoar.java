package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class Bezoar extends BaseCurioItem implements EffectInvul.Poison {
    public Bezoar() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.bezoar.info"),
            Component.translatable("item.confluence.bezoar.info2"),
            Component.translatable("item.confluence.bezoar.info3")
        };
    }
}
