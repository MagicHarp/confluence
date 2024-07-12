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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class Shackle extends BaseCurioItem {
    public static final UUID ARMOR_UUID = UUID.fromString("AD2BC718-506A-D43B-BE7A-98F60EE41A33");
    private static ImmutableMultimap<Attribute, AttributeModifier> ARMOR;

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ARMOR == null) {
            ARMOR = ImmutableMultimap.of(
                Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "Shackle", ModConfigs.SHACKLE_ARMOR.get(), AttributeModifier.Operation.ADDITION)
            );
        }
        return ARMOR;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.shackle.info"),
            Component.translatable("item.confluence.shackle.info2")
        };
    }
}
