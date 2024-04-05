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
import net.minecraftforge.common.ForgeMod;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class BerserkersGlove extends BaseCurioItem implements IAggroAttach {
    public static final UUID ARMOR_UUID = UUID.fromString("4CB3ACFA-DD90-C6EA-3FD2-95469EFE2AA4");
    public static final UUID ATTACK_SPEED_UUID = UUID.fromString("7736D2EF-0A57-D622-A8B0-2C4942EC572A");
    public static final UUID KNOCK_BACK_UUID = UUID.fromString("DF71E32B-22B5-8F93-4550-12A6B8E98B4B");
    public static final UUID DISTANCE_UUID = UUID.fromString("65B1BF75-500B-76D2-1FE6-F4510BFDEF63");
    private static final ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE = ImmutableMultimap.of(
        Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "Berserkers Glove", 8, AttributeModifier.Operation.ADDITION),
        Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_UUID, "Berserkers Glove", 0.12, AttributeModifier.Operation.MULTIPLY_TOTAL),
        Attributes.ATTACK_KNOCKBACK, new AttributeModifier(KNOCK_BACK_UUID, "Berserkers Glove", 1, AttributeModifier.Operation.MULTIPLY_TOTAL),
        ForgeMod.ENTITY_REACH.get(), new AttributeModifier(DISTANCE_UUID, "Berserkers Glove", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    @Override
    public int getAggro() {
        return 400;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return ATTRIBUTE;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("item.confluence.berserkers_glove.tooltip2"));
    }
}
