package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.datagen.limit.CustomName;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class ExplorersEquipment extends BaseCurioItem implements EffectInvul.MiningFatigue, EffectInvul.Levitation, CustomName {
    public ExplorersEquipment() {
        super(ModRarity.PINK);
    }

    @Override
    public String getGenName() {
        return "Explorer's Equipment";
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.explorers_equipment.info"),
            Component.translatable("item.confluence.explorers_equipment.info2")
        };
    }
}
