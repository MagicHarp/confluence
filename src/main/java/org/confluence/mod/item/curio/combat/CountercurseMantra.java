package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class CountercurseMantra extends BaseCurioItem implements EffectInvul.Silenced, EffectInvul.Cursed {
    public CountercurseMantra() {
        super(ModRarity.PINK);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.countercurse_mantra.info"),
            Component.translatable("item.confluence.countercurse_mantra.info2"),
            Component.translatable("item.confluence.countercurse_mantra.info3")
        };
    }
}
