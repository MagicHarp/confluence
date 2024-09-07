package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModRarity;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class SniperScope extends RifleScope {
    public static final UUID CRIT_UUID = UUID.fromString("EF0F0AE1-685D-D312-CD47-78ABA87308A2");
    public static final UUID RANGED_UUID = UUID.fromString("815F8A67-C78D-B4FC-1C18-19C531DB0225");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTES;

    public SniperScope() {
        super(ModRarity.LIME);
    }

    public SniperScope(Rarity rarity) {
        super(rarity);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTES == null) {
            ATTRIBUTES = ImmutableMultimap.of(
                ModAttributes.getCriticalChance(), new AttributeModifier(CRIT_UUID, "Sniper Scope", 0.1, AttributeModifier.Operation.ADDITION),
                ModAttributes.getRangedDamage(), new AttributeModifier(RANGED_UUID, "Sniper Scope", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL)
            );
        }
        return ATTRIBUTES;
    }
}
