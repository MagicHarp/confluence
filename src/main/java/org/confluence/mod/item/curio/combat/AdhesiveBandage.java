package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class AdhesiveBandage extends BaseCurioItem implements EffectInvul.Bleeding {
    public AdhesiveBandage() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.adhesive_bandage.info")
        };
    }
}
