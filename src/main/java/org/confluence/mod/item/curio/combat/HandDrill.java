package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class HandDrill extends BaseCurioItem implements EffectInvul.MiningFatigue {
    public HandDrill() {
        super(ModRarity.LIGHT_RED);
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.hand_drill.info"),
            Component.translatable("item.confluence.hand_drill.info2")
        };
    }
}
