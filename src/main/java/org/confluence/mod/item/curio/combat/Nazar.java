package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class Nazar extends BaseCurioItem implements EffectInvul.Cursed {
    public Nazar() {
        super(ModRarity.GREEN);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.nazar.info")
        };
    }
}
