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

public class PutridScent extends BaseCurioItem implements IAggroAttach, ICriticalHit {
    public static final UUID DAMAGE_UUID = UUID.fromString("70F6E4B4-64AC-4B2A-AAD6-8C35AFB9507D");
    private static final ImmutableMultimap<Attribute, AttributeModifier> DAMAGE = ImmutableMultimap.of(
        Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_UUID, "Putrid Scent", 0.05, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    public PutridScent() {
        super(ModRarity.LIGHT_PURPLE);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return DAMAGE;
    }

    @Override
    public int getAggro() {
        return -400;
    }

    @Override
    public double getChance() {
        return 0.05;
    }
}
