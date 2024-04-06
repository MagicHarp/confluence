package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class FeralClaws extends BaseCurioItem implements IContinueSwing {
    public static final UUID ATTACK_SPEED_UUID = UUID.fromString("069AEF75-87F4-9B81-A3D1-82114C18103D");
    private static final ImmutableMultimap<Attribute, AttributeModifier> ATTACK_SPEED = ImmutableMultimap.of(
        Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_UUID, "Feral Claws", 0.12, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    public FeralClaws() {
        super(ModRarity.ORANGE);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return ATTACK_SPEED;
    }
}
