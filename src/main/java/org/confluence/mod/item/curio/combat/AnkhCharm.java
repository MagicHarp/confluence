package org.confluence.mod.item.curio.combat;

import net.minecraft.world.item.Rarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class AnkhCharm extends BaseCurioItem implements EffectInvul.Poison, EffectInvul.Blindness, EffectInvul.Slowness, EffectInvul.Weakness,
    EffectInvul.Bleeding, EffectInvul.BrokenArmor, EffectInvul.Confused, EffectInvul.Cursed, EffectInvul.Silenced, EffectInvul.Stoned {
    public AnkhCharm(Rarity rarity) {
        super(rarity);
    }

    public AnkhCharm() {
        super(ModRarity.LIGHT_PURPLE);
    }
}
