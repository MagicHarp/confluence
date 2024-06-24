package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class Flashlight extends BaseCurioItem implements EffectInvul.Darkness {
    public Flashlight() {
        super(ModRarity.LIGHT_RED);
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.flashlight.info"),
            Component.translatable("item.confluence.flashlight.info2")
        };
    }
}
