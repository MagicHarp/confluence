package org.confluence.mod.item.curio.movement;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.confluence.mod.item.curio.ILavaImmune;
import org.confluence.mod.item.curio.combat.IFireImmune;
import org.confluence.mod.item.curio.combat.ILavaHurtReduce;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class TerrasparkBoots extends FrostsparkBoots implements IFireImmune, ILavaImmune, ILavaHurtReduce, IFluidWalk {
    public static final UUID SPEED_UUID = UUID.fromString("73456995-B70B-48D9-7EA4-191BD76C94C9");
    private static final AttributeModifier SPEED_MODIFIER = new AttributeModifier(SPEED_UUID, "Terraspark Boots", 0.08, AttributeModifier.Operation.MULTIPLY_TOTAL);

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        IceSkates.tick(slotContext.entity(), stack);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        if (living != null) {
            if (stack.getOrCreateTag().getBoolean("onPosIsIce")) {
                return ImmutableMultimap.of(
                    Attributes.MOVEMENT_SPEED, SPEED_MODIFIER,
                    Attributes.MOVEMENT_SPEED, getSpeedModifier(stack),
                    Attributes.MOVEMENT_SPEED, IceSkates.MODIFIER
                );
            } else {
                AttributeInstance attribute = living.getAttribute(Attributes.MOVEMENT_SPEED);
                if (attribute != null) attribute.removeModifier(IceSkates.SPEED_UUID);
            }
        }
        return ImmutableMultimap.of(
            Attributes.MOVEMENT_SPEED, SPEED_MODIFIER,
            Attributes.MOVEMENT_SPEED, getSpeedModifier(stack)
        );
    }

    @Override
    public boolean canStandOn(FluidState fluidState) {
        return fluidState.is(Fluids.WATER) || fluidState.is(Fluids.LAVA);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(IFluidWalk.ALL_FLUID);
        list.add(Component.translatable("item.confluence.terraspark_boots.tooltip2"));
        list.add(ILavaHurtReduce.TOOLTIP);
    }
}
