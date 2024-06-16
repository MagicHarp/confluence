package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class Megaphone extends BaseCurioItem implements EffectInvul.Silenced {
    public Megaphone() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.megaphone.info"),
                Component.translatable("item.confluence.megaphone.info2"),
                Component.translatable("item.confluence.megaphone.info3")
        };
    }
}
