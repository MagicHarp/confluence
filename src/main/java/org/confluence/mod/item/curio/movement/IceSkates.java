package org.confluence.mod.item.curio.movement;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.block.natural.ThinIceBlock;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class IceSkates extends BaseCurioItem implements ThinIceBlock.IceSafe {
    public static final UUID SPEED_UUID = UUID.fromString("7691D447-BD87-29E8-1C04-CF218DFF154D");
    static final ImmutableMultimap<Attribute, AttributeModifier> SPEED = ImmutableMultimap.of(
        Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_UUID, "Ice Skates", 0.3, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
        return EMPTY_TOOLTIP;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        if (living != null && living.level().getBlockState(living.getOnPos().below()).is(BlockTags.ICE)) {
            return SPEED;
        }
        return EMPTY_ATTRIBUTE;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("item.confluence.ice_skates.tooltip2"));
    }
}
