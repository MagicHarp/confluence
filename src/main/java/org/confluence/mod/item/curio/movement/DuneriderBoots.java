package org.confluence.mod.item.curio.movement;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class DuneriderBoots extends BaseSpeedBoots {
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        CompoundTag nbt = stack.getOrCreateTag();
        if (living.level().getBlockState(living.getOnPos().below()).is(BlockTags.SAND)) {
            speedUp(slotContext, nbt, 2, 70);
        } else {
            speedUp(slotContext, nbt, 1, 40);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("item.confluence.dunerider_boots.tooltip2"));
    }
}
