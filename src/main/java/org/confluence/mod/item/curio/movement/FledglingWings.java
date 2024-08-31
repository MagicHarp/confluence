package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import org.confluence.mod.misc.ModRarity;

public class FledglingWings extends BaseWings {

    public FledglingWings() {
        super(ModRarity.WHITE, 25, 1.5F);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.fledgling_wings.info")
        };
    }
}
