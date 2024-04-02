package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class AvengerEmblem extends BaseCurioItem {
    public static final UUID DAMAGE_UUID = UUID.fromString("3D20DB42-C40E-23BF-6CE4-FBDD7CC14222");
    private static final ImmutableMultimap<Attribute, AttributeModifier> DAMAGE = ImmutableMultimap.of(
        Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_UUID, "Avenger Emblem", 0.12, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return DAMAGE;
    }
}
