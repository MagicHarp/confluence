package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class ReflectiveShades extends BaseCurioItem implements EffectInvul.Blindness, EffectInvul.Stoned {
    public ReflectiveShades() {
        super(ModRarity.PINK);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.reflective_shades.info"),
            Component.translatable("item.confluence.reflective_shades.info2")
        };
    }
}
