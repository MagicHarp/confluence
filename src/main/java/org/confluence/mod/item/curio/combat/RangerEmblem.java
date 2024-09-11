package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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

public class RangerEmblem extends BaseCurioItem {
    public static final UUID RANGED_UUID = UUID.fromString("F614C1BC-DBB7-04EA-6BA7-C19AA02D4F99");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE;

    public RangerEmblem() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTE == null) {
            ATTRIBUTE = ImmutableMultimap.of(
                ModAttributes.getRangedDamage(), new AttributeModifier(RANGED_UUID, "Ranger Emblem", 0.15, AttributeModifier.Operation.MULTIPLY_TOTAL)
            );
        }
        return ATTRIBUTE;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {}
}
