package org.confluence.mod.item.curio.movement;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class Aglet extends BaseCurioItem {
    public static final UUID SPEED_UUID = UUID.fromString("2B6DC797-A802-DF05-8231-BC8FCA9D770A");
    private static final ImmutableMultimap<Attribute, AttributeModifier> SPEED = ImmutableMultimap.of(
        Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_UUID, "Aglet", 0.05, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return SPEED;
    }
}
