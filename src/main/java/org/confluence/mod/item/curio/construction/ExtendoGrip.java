package org.confluence.mod.item.curio.construction;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import org.confluence.mod.item.curio.miscellaneous.BaseCurioItem;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class ExtendoGrip extends BaseCurioItem {
    public static final UUID REACH_UUID = UUID.fromString("E4F0BF95-655D-7657-F58E-1C7053FACAFC");
    private static final ImmutableMultimap<Attribute, AttributeModifier> REACH = ImmutableMultimap.of(
        ForgeMod.BLOCK_REACH.get(), new AttributeModifier(REACH_UUID, "Extendo Grip", 3, AttributeModifier.Operation.ADDITION)
    );

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return REACH;
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        list.add(0, Component.translatable("item.confluence.extendo_grip.tooltip"));
    }
}
