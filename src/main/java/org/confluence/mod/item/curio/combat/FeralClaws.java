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
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class FeralClaws extends BaseCurioItem implements IAutoAttack {
    public static final UUID ATTACK_SPEED_UUID = UUID.fromString("069AEF75-87F4-9B81-A3D1-82114C18103D");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTACK_SPEED;

    public FeralClaws() {
        super(ModRarity.ORANGE);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTACK_SPEED == null) {
            ATTACK_SPEED = ImmutableMultimap.of(
                Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_UUID, "Feral Claws", ModConfigs.FERAL_CLAWS_SPEED.get(), AttributeModifier.Operation.MULTIPLY_TOTAL)
            );
        }
        return ATTACK_SPEED;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(TOOLTIP);
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.feral_claws.info"),
            Component.translatable("item.confluence.feral_claws.info2")
        };
    }
}
