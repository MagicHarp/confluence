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

public class TitanGlove extends BaseCurioItem {
    public static final UUID KNOCK_BACK_UUID = UUID.fromString("70F6E4B4-64AC-4B2A-AAD6-8C35AFB9507D");
    public static final UUID DISTANCE_UUID = UUID.fromString("109E47B7-FCFF-0A0C-78B1-1AD8EA1AD7D4");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE;

    public TitanGlove() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTE == null) {
            ATTRIBUTE = ImmutableMultimap.of(
                Attributes.ATTACK_KNOCKBACK, new AttributeModifier(KNOCK_BACK_UUID, "Titan Glove", ModConfigs.TITAN_GLOVE_KNOCKBACK.get(), AttributeModifier.Operation.MULTIPLY_TOTAL),
                ForgeMod.ENTITY_REACH.get(), new AttributeModifier(DISTANCE_UUID, "Titan Glove", ModConfigs.TITAN_GLOVE_REACH.get(), AttributeModifier.Operation.MULTIPLY_TOTAL)
            );
        }
        return ATTRIBUTE;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.titan_glove.info")
        };
    }
}
