package org.confluence.mod.item.curio.expert;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class HivePack extends BaseCurioItem implements ModRarity.Expert {
    public HivePack() {
        super(ModRarity.EXPERT);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.hive_pack.info"),
                Component.translatable("item.confluence.hive_pack.info2")
        };
    }
}
