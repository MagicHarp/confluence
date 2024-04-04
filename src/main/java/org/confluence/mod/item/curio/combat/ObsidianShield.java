package org.confluence.mod.item.curio.combat;

import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class ObsidianShield extends BaseCurioItem implements IFireImmune {
    public ObsidianShield() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return CobaltShield.ATTRIBUTE;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
        return EMPTY_TOOLTIP;
    }
}
