package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class ArmorBracing extends BaseCurioItem implements EffectInvul.BrokenArmor, EffectInvul.Weakness {
    public ArmorBracing() {
        super(ModRarity.PINK);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.armor_bracing.info"),
                Component.translatable("item.confluence.armor_bracing.info2"),
                Component.translatable("item.confluence.armor_bracing.info3")
        };
    }
}
