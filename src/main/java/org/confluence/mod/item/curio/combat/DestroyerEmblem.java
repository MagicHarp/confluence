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

public class DestroyerEmblem extends BaseCurioItem implements ICriticalHit, IMagicAttack, IProjectileAttack {
    public static final UUID DAMAGE_UUID = UUID.fromString("35E7BAD6-5998-D35B-2974-4FA8065D29F7");
    private static final ImmutableMultimap<Attribute, AttributeModifier> DAMAGE = ImmutableMultimap.of(
        Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_UUID, "Destroyer Emblem", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    public DestroyerEmblem() {
        super(ModRarity.LIME);
    }

    @Override
    public double getChance() {
        return 0.08;
    }

    @Override
    public double getMagicBonus() {
        return 0.1;
    }

    @Override
    public float getProjectileBonus() {
        return 0.1F;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return DAMAGE;
    }
}
