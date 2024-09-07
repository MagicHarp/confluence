package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class DestroyerEmblem extends BaseCurioItem {
    public static final UUID DAMAGE_UUID = UUID.fromString("35E7BAD6-5998-D35B-2974-4FA8065D29F7");
    public static final UUID CRIT_UUID = UUID.fromString("4BBCB7D4-9884-124C-041C-72CA6959D7E8");
    public static final UUID RANGED_UUID = UUID.fromString("D8663255-AC10-B739-4E1E-8339106CD1A8");
    public static final UUID MAGIC_UUID = UUID.fromString("DFC7E336-6BF4-D817-FDCE-2836A0D906C2");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTES;

    public DestroyerEmblem() {
        super(ModRarity.LIME);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTES == null) {
            ATTRIBUTES = ImmutableMultimap.of(
                    Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_UUID, "Destroyer Emblem", ModConfigs.DESTROYER_EMBLEM_DAMAGE.get(), AttributeModifier.Operation.MULTIPLY_TOTAL),
                    ModAttributes.getCriticalChance(), new AttributeModifier(CRIT_UUID, "Destroyer Emblem", ModConfigs.DESTROYER_EMBLEM_CRITICAL_CHANCE.get(), AttributeModifier.Operation.ADDITION),
                    ModAttributes.getRangedDamage(), new AttributeModifier(RANGED_UUID, "Destroyer Emblem", ModConfigs.DESTROYER_EMBLEM_RANGED.get(), AttributeModifier.Operation.MULTIPLY_TOTAL),
                    ModAttributes.getMagicDamage(), new AttributeModifier(MAGIC_UUID, "Destroyer Emblem", ModConfigs.DESTROYER_EMBLEM_MAGIC.get(), AttributeModifier.Operation.MULTIPLY_TOTAL)
            );
        }
        return ATTRIBUTES;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {}
}
