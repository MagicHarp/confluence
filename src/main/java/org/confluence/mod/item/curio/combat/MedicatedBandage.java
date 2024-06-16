package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class MedicatedBandage extends BaseCurioItem implements EffectInvul.Poison, EffectInvul.Bleeding {
    public MedicatedBandage() {
        super(ModRarity.PINK);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.medicated_bandage.info"),
                Component.translatable("item.confluence.medicated_bandage.info2"),
                Component.translatable("item.confluence.medicated_bandage.info3")
        };
    }
}
