package org.confluence.mod.item.curio.movement;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class IceSkates extends BaseCurioItem {
    public static final UUID SPEED_UUID = UUID.fromString("7691D447-BD87-29E8-1C04-CF218DFF154D");
    static final AttributeModifier MODIFIER = new AttributeModifier(SPEED_UUID, "Ice Skates", 0.3, AttributeModifier.Operation.MULTIPLY_TOTAL);
    static final ImmutableMultimap<Attribute, AttributeModifier> SPEED = ImmutableMultimap.of(Attributes.MOVEMENT_SPEED, MODIFIER);

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
        return EMPTY_TOOLTIP;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        tick(slotContext.entity(), stack);
    }

    static void tick(LivingEntity living, ItemStack itemStack) {
        if (living.level().isClientSide) return;
        itemStack.getOrCreateTag().putBoolean("onPosIsIce", living.level().getBlockState(living.getOnPos()).is(BlockTags.ICE));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        if (living != null) {
            if (stack.getOrCreateTag().getBoolean("onPosIsIce")) {
                return SPEED;
            } else {
                AttributeInstance attribute = living.getAttribute(Attributes.MOVEMENT_SPEED);
                if (attribute != null) attribute.removeModifier(IceSkates.SPEED_UUID);
            }
        }
        return EMPTY_ATTRIBUTE;
    }
}
