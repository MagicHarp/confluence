package org.confluence.mod.item.curio.construction;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class PortableCementMixer extends BaseCurioItem implements IRightClickSubtractor {
    public PortableCementMixer() {
        super(ModRarity.ORANGE);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.portable_cement_mixer.info"),
            Component.translatable("item.confluence.portable_cement_mixer.info2")
        };
    }
}
