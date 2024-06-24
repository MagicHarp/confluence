package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class AnkhCharm extends BaseCurioItem implements EffectInvul.Poison, EffectInvul.Hunger, EffectInvul.MiningFatigue, EffectInvul.Weakness, EffectInvul.Levitation, EffectInvul.Wither, EffectInvul.Darkness, EffectInvul.Blindness, EffectInvul.Nausea, EffectInvul.Slowness {
    public AnkhCharm() {
        super(ModRarity.LIGHT_PURPLE);
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.ankh_charm.info"),
            Component.translatable("item.confluence.ankh_charm.info2")
        };
    }
}
