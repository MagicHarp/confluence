package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class CobaltShield extends BaseCurioItem {
    public static final UUID RESISTANCE_UUID = UUID.fromString("358E02EF-951D-84B6-4ED4-1E03AF3520E2");
    public static final UUID ARMOR_UUID = UUID.fromString("113FC2B9-A34D-7A7C-D928-27321947F59C");
    static final ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE = ImmutableMultimap.of(
        Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(RESISTANCE_UUID, "Cobalt Shield", 1.0, AttributeModifier.Operation.ADDITION),
        Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "Cobalt Shield", 1.0, AttributeModifier.Operation.ADDITION)
    );

    public CobaltShield() {
        super(ModRarity.GREEN);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return ATTRIBUTE;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
        return EMPTY_TOOLTIP;
    }
}
