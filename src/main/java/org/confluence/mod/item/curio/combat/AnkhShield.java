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
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class AnkhShield extends AnkhCharm implements IFireImmune {
    public static final UUID RESISTANCE_UUID = UUID.fromString("B9B53B92-98E3-05EF-9D6C-85D8E110B2DC");
    public static final UUID ARMOR_UUID = UUID.fromString("147A80CE-4C69-8073-9340-F851F5BFC622");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE;

    public AnkhShield() {
        super(ModRarity.LIME);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTE == null) {
            ATTRIBUTE = ImmutableMultimap.of(
                Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(RESISTANCE_UUID, "Ankh Shield", ModConfigs.ANKH_SHIELD_RESISTANCE.get(), AttributeModifier.Operation.ADDITION),
                Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "Ankh Shield", ModConfigs.ANKH_SHIELD_ARMOR.get(), AttributeModifier.Operation.ADDITION)
            );
        }
        return ATTRIBUTE;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(TOOLTIP);
        list.add(Component.translatable("item.confluence.ankh_charm.tooltip"));
    }
}
