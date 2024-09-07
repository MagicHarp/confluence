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
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class FleshKnuckles extends BaseCurioItem {
    public static final UUID ARMOR_UUID = UUID.fromString("91F63796-420A-FFCB-504C-FEAA53C7DFC4");
    public static final UUID AGGRO_UUID = UUID.fromString("D22468D3-5FC6-A35C-A1CC-C27F398BE5DF");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTES;

    public FleshKnuckles() {
        super(ModRarity.PINK);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTES == null) {
            ATTRIBUTES = ImmutableMultimap.of(
                    Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "Flesh Knuckles", 8, AttributeModifier.Operation.ADDITION),
                    ModAttributes.getAggro(), new AttributeModifier(AGGRO_UUID, "Flesh Knuckles", 400, AttributeModifier.Operation.ADDITION)
            );
        }
        return ATTRIBUTES;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("curios.tooltip.aggro_attach"));
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.flesh_knuckles.info"),
            Component.translatable("item.confluence.flesh_knuckles.info2")
        };
    }
}
