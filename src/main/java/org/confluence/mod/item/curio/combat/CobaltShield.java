package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class CobaltShield extends BaseCurioItem {
    public static final UUID RESISTANCE_UUID = UUID.fromString("358E02EF-951D-84B6-4ED4-1E03AF3520E2");
    public static final UUID ARMOR_UUID = UUID.fromString("113FC2B9-A34D-7A7C-D928-27321947F59C");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE;

    public CobaltShield() {
        super(ModRarity.GREEN);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTE == null) {
            ATTRIBUTE = ImmutableMultimap.of(
                Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(RESISTANCE_UUID, "Cobalt Shield", ModConfigs.COBALT_SHIELD_RESISTANCE.get(), AttributeModifier.Operation.ADDITION),
                Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "Cobalt Shield", ModConfigs.COBALT_SHIELD_ARMOR.get(), AttributeModifier.Operation.ADDITION)
            );
        }
        return ATTRIBUTE;
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.cobalt_shield.info")
        };
    }
}
