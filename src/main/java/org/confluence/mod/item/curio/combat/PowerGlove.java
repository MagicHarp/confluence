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
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class PowerGlove extends BaseCurioItem implements IAutoAttack {
    public static final UUID ATTACK_SPEED_UUID = UUID.fromString("8DE4D89E-29B7-6BF0-2581-7868FA489433");
    public static final UUID KNOCK_BACK_UUID = UUID.fromString("4D04974E-FCA5-11DE-2C75-F29679DE9CF1");
    public static final UUID DISTANCE_UUID = UUID.fromString("955047C4-BF9C-D87C-9B6D-05F274392BD4");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE;

    public PowerGlove() {
        super(ModRarity.PINK);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTE == null) {
            ATTRIBUTE = ImmutableMultimap.of(
                Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_UUID, "Power Glove", ModConfigs.POWER_GLOVE_SPEED.get(), AttributeModifier.Operation.MULTIPLY_TOTAL),
                Attributes.ATTACK_KNOCKBACK, new AttributeModifier(KNOCK_BACK_UUID, "Power Glove", ModConfigs.POWER_GLOVE_KNOCKBACK.get(), AttributeModifier.Operation.MULTIPLY_TOTAL),
                ForgeMod.ENTITY_REACH.get(), new AttributeModifier(DISTANCE_UUID, "Power Glove", ModConfigs.POWER_GLOVE_REACH.get(), AttributeModifier.Operation.MULTIPLY_TOTAL)
            );
        }
        return ATTRIBUTE;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(TOOLTIP);
    }
}
