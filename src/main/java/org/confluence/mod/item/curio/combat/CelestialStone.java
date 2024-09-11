package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModRarity;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class CelestialStone extends BaseCurioItem {
    public static final UUID ATTACK_SPEED_UUID = UUID.fromString("A1F8AB0C-8285-3BE9-575A-E05787707241");
    public static final UUID DAMAGE_UUID = UUID.fromString("2B80C158-EBB2-39C0-E246-E401C544D9D8");
    public static final UUID CRIT_UUID = UUID.fromString("6057460F-D258-0529-6891-2BD9336D36C2");
    public static final UUID ARMOR_UUID = UUID.fromString("814ABB7D-ADB4-F0C6-B7BD-A2E3FB23EE8D");
    public static final UUID MINING_UUID = UUID.fromString("11644D19-DAE5-3871-3C77-FC6DF7972AA4");
    public static final UUID RANGED_UUID = UUID.fromString("9AA47009-7031-6A8F-7168-330DDF72E53C");
    public static final UUID MAGIC_UUID = UUID.fromString("0922FF1E-D970-D952-6A17-1363678FB9D0");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTES;

    public CelestialStone() {
        super(ModRarity.LIME);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTES == null) {
            ATTRIBUTES = ImmutableMultimap.<Attribute, AttributeModifier>builder()
                    .put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_UUID, "Celestial Stone", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL))
                    .put(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_UUID, "Celestial Stone", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL))
                    .put(ModAttributes.getCriticalChance(), new AttributeModifier(CRIT_UUID, "Celestial Stone", 0.02, AttributeModifier.Operation.ADDITION))
                    .put(Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "Celestial Stone", 4, AttributeModifier.Operation.ADDITION))
                    .put(ModAttributes.getMiningSpeed(), new AttributeModifier(MINING_UUID, "Celestial Stone", 0.15, AttributeModifier.Operation.MULTIPLY_TOTAL))
                    .put(ModAttributes.getRangedDamage(), new AttributeModifier(RANGED_UUID, "Celestial Stone", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL))
                    .put(ModAttributes.getMagicDamage(), new AttributeModifier(MAGIC_UUID, "Celestial Stone", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL))
                    .build();
        }
        return ATTRIBUTES;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        ModEffects.healPerSecond(slotContext.entity(), 2.0F);
    }
}
