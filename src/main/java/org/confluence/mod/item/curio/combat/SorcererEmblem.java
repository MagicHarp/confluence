package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class SorcererEmblem extends BaseCurioItem implements IMagicAttack {
    public SorcererEmblem() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public double getMagicBonus() {
        return 0.15;
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.sorcerer_emblem.info")
        };
    }
}
