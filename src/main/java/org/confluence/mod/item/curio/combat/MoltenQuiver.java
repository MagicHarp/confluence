package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class MoltenQuiver extends MagicQuiver {
    public static final UUID RANGED_DAMAGE_UUID = UUID.fromString("017744D3-2187-005A-F0EB-FF16BB959070");
    public static final UUID RANGED_VELOCITY_UUID = UUID.fromString("AE592B4F-89B5-ECA3-269E-F7F9589CE7C1");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTES;

    public MoltenQuiver() {
        super(ModRarity.PINK);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTES == null) {
            ATTRIBUTES = ImmutableMultimap.of(
                ModAttributes.getRangedDamage(), new AttributeModifier(RANGED_DAMAGE_UUID, "Molten Quiver", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL),
                ModAttributes.getRangedVelocity(), new AttributeModifier(RANGED_VELOCITY_UUID, "Molten Quiver", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL)
            );
        }
        return ATTRIBUTES;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("item.confluence.molten_quiver.tooltip"));
        list.add(Component.translatable("item.confluence.molten_quiver.tooltip2"));
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.molten_quiver.info"),
            Component.translatable("item.confluence.molten_quiver.info2")
        };
    }
}
