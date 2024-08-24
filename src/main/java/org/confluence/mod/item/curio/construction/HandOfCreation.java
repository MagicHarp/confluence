package org.confluence.mod.item.curio.construction;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import org.confluence.mod.item.IRangePickup;
import org.confluence.mod.item.curio.movement.StepStool;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class HandOfCreation extends StepStool implements IRightClickSubtractor, IBreakSpeedBonus, IRangePickup {
    public static final UUID REACH_UUID = UUID.fromString("25EAB0F2-81C1-254D-156E-7CDAEBA54DC2");
    private static final ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE = ImmutableMultimap.of(
        ForgeMod.BLOCK_REACH.get(), new AttributeModifier(REACH_UUID, "Hand Of Creation", 3, AttributeModifier.Operation.ADDITION)
    );

    public HandOfCreation() {
        super(ModRarity.LIGHT_PURPLE);
    }

    @Override
    public float getBreakBonus() {
        return 0.25F;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.hand_of_creation.tooltip"));
        list.add(Component.translatable("item.confluence.hand_of_creation.tooltip2"));
        list.add(Component.translatable("item.confluence.hand_of_creation.tooltip3"));
        list.add(StepStool.TOOLTIP);
        if (itemStack.getTag() != null) {
            list.add(Component.translatable("item.confluence.step_stool.tooltip2", itemStack.getTag().getInt("extraStep"))
                .withStyle(style -> style.withColor(ChatFormatting.BLUE)));
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return ATTRIBUTE;
    }
}
