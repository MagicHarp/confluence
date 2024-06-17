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
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class FeralClaws extends BaseCurioItem implements IAutoAttack {
    public static final UUID ATTACK_SPEED_UUID = UUID.fromString("069AEF75-87F4-9B81-A3D1-82114C18103D");
    private static final ImmutableMultimap<Attribute, AttributeModifier> ATTACK_SPEED = ImmutableMultimap.of(
        Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_UUID, "Feral Claws", 0.12, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    public FeralClaws() {
        super(ModRarity.ORANGE);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return ATTACK_SPEED;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(TOOLTIP);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.feral_claws.info"),
            Component.translatable("item.confluence.feral_claws.info2"),
            Component.translatable("item.confluence.feral_claws.info3")
        };
    }
}
