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
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModConfigs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class BerserkersGlove extends BaseCurioItem implements IAutoAttack {
    public static final UUID ARMOR_UUID = UUID.fromString("4CB3ACFA-DD90-C6EA-3FD2-95469EFE2AA4");
    public static final UUID ATTACK_SPEED_UUID = UUID.fromString("7736D2EF-0A57-D622-A8B0-2C4942EC572A");
    public static final UUID KNOCK_BACK_UUID = UUID.fromString("DF71E32B-22B5-8F93-4550-12A6B8E98B4B");
    public static final UUID DISTANCE_UUID = UUID.fromString("65B1BF75-500B-76D2-1FE6-F4510BFDEF63");
    public static final UUID AGGRO_UUID = UUID.fromString("D918E5BA-E38B-7D46-51B6-53A573AA8079");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTES;

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTES == null) {
            ATTRIBUTES = ImmutableMultimap.<Attribute, AttributeModifier>builder()
                    .put(Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "Berserkers Glove", ModConfigs.BERSERKERS_GLOVE_ARMOR.get(), AttributeModifier.Operation.ADDITION))
                    .put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_UUID, "Berserkers Glove", ModConfigs.BERSERKERS_GLOVE_SPEED.get(), AttributeModifier.Operation.MULTIPLY_TOTAL))
                    .put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(KNOCK_BACK_UUID, "Berserkers Glove", ModConfigs.BERSERKERS_GLOVE_KNOCKBACK.get(), AttributeModifier.Operation.MULTIPLY_TOTAL))
                    .put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(DISTANCE_UUID, "Berserkers Glove", ModConfigs.BERSERKERS_GLOVE_REACH.get(), AttributeModifier.Operation.MULTIPLY_TOTAL))
                    .put(ModAttributes.getAggro(), new AttributeModifier(AGGRO_UUID, "Berserkers Glove", ModConfigs.BERSERKERS_GLOVE_AGGRO.get(), AttributeModifier.Operation.ADDITION))
                    .build();
        }
        return ATTRIBUTES;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IAutoAttack.TOOLTIP);
        list.add(Component.translatable("curios.tooltip.aggro_attach"));
    }

    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.berserkers_glove.info"),
                Component.translatable("item.confluence.berserkers_glove.info2")
        };
    }
}
