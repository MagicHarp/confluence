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
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class MechanicalGlove extends BaseCurioItem implements IContinueSwing {
    public static final UUID DAMAGE_UUID = UUID.fromString("36E88BBC-C5CD-79B7-7E1D-144E6C3C5818");
    public static final UUID ATTACK_SPEED_UUID = UUID.fromString("32488AC2-6D30-E40C-2722-8630E4BA101E");
    public static final UUID KNOCK_BACK_UUID = UUID.fromString("FDA86C95-41F9-1A50-EF0B-A9B9AFFD5AB3");
    public static final UUID DISTANCE_UUID = UUID.fromString("95983B7C-53EF-E066-32E2-B4FDCA0C5F00");
    private static final ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE = ImmutableMultimap.of(
        Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_UUID, "Mechanical Glove", 0.12, AttributeModifier.Operation.MULTIPLY_TOTAL),
        Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_UUID, "Mechanical Glove", 0.12, AttributeModifier.Operation.MULTIPLY_TOTAL),
        Attributes.ATTACK_KNOCKBACK, new AttributeModifier(KNOCK_BACK_UUID, "Mechanical Glove", 1, AttributeModifier.Operation.MULTIPLY_TOTAL),
        ForgeMod.ENTITY_REACH.get(), new AttributeModifier(DISTANCE_UUID, "Mechanical Glove", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    public MechanicalGlove() {
        super(ModRarity.LIGHT_PURPLE);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return ATTRIBUTE;
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("item.confluence.mechanical_glove.tooltip2"));
    }
}
