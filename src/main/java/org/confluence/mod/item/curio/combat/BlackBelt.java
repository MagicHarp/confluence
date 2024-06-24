package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class BlackBelt extends BaseCurioItem implements IHurtEvasion {
    public BlackBelt() {
        super(ModRarity.LIME);
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.black_belt.info"),
            Component.translatable("item.confluence.black_belt.info2")
        };
    }
}
