package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class DetoxificationCapsule extends BaseCurioItem implements EffectInvul.Poison, EffectInvul.Wither {
    public DetoxificationCapsule() {
        super(ModRarity.PINK);
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.detoxification_capsule.info"),
            Component.translatable("item.confluence.detoxification_capsule.info2")
        };
    }
}
