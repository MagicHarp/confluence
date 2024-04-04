package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class PowerGlove extends BaseCurioItem {
    public static final UUID ATTACK_SPEED_UUID = UUID.fromString("8DE4D89E-29B7-6BF0-2581-7868FA489433");
    public static final UUID KNOCK_BACK_UUID = UUID.fromString("4D04974E-FCA5-11DE-2C75-F29679DE9CF1");
    public static final UUID DISTANCE_UUID = UUID.fromString("955047C4-BF9C-D87C-9B6D-05F274392BD4");
    private static final ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE = ImmutableMultimap.of(
        Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_UUID, "Power Glove", 0.12, AttributeModifier.Operation.MULTIPLY_TOTAL),
        Attributes.ATTACK_KNOCKBACK, new AttributeModifier(KNOCK_BACK_UUID, "Power Glove", 1, AttributeModifier.Operation.MULTIPLY_TOTAL),
        ForgeMod.ENTITY_REACH.get(), new AttributeModifier(DISTANCE_UUID, "Power Glove", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    public PowerGlove() {
        super(ModRarity.PINK);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return ATTRIBUTE;
    }
}
