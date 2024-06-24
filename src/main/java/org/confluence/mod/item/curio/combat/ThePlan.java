package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class ThePlan extends BaseCurioItem implements EffectInvul.Slowness, EffectInvul.Nausea {
    public ThePlan() {
        super(ModRarity.PINK);
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.the_plan.info"),
            Component.translatable("item.confluence.the_plan.info2")
        };
    }
}
