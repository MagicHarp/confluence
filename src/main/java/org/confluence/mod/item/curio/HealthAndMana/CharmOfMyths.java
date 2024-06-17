package org.confluence.mod.item.curio.HealthAndMana;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.misc.ModRarity;
import top.theillusivec4.curios.api.SlotContext;

public class CharmOfMyths extends PhilosophersStone {
    public CharmOfMyths() {
        super(ModRarity.LIGHT_PURPLE);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        ModEffects.heal(slotContext.entity(), 1);
    }

    @Override
    public String getGenName() {
        return "Charm Of Myths";
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.charm_of_myths.info"),
            Component.translatable("item.confluence.charm_of_myths.info2"),
            Component.translatable("item.confluence.charm_of_myths.info3")
        };
    }
}
