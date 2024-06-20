package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class CobaltShield extends BaseCurioItem {
    private static final ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE;

    static {
        ATTRIBUTE = ImmutableMultimap.<Attribute, AttributeModifier>builder()
            .put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.fromString("358E02EF-951D-84B6-4ED4-1E03AF3520E2"), "Cobalt Shield", 1.0, AttributeModifier.Operation.ADDITION))
            .put(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("minecraft", "generic.armor")), new AttributeModifier(UUID.fromString("113FC2B9-A34D-7A7C-D928-27321947F59C"), "Cobalt Shield", 1.5, AttributeModifier.Operation.MULTIPLY_BASE))
            .put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.fromString("ACB63685-6E1F-DD05-1343-259CD8303D5F"), "Cobalt Shield", 1.0, AttributeModifier.Operation.MULTIPLY_TOTAL))
            .build();
    }

    public CobaltShield() {
        super(ModRarity.GREEN);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return ATTRIBUTE;
    }
}
