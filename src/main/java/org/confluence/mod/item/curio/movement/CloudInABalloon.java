package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class CloudInABalloon extends BaseCurioItem implements IMultiJump, IJumpBoost {
    @Override
    public double getBoost() {
        return 0.33;
    }

    @Override
    public int getJumpTimes() {
        return 1;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        if (living instanceof ServerPlayer serverPlayer) {
            IMultiJump.sendMaxJump(serverPlayer);
        }
        IJumpBoost.getMaxBoost(living);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        if (living instanceof ServerPlayer serverPlayer) {
            IMultiJump.sendMaxJump(serverPlayer);
        }
        IJumpBoost.getMaxBoost(living);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("item.confluence.cloud_in_a_balloon.tooltip2"));
    }
}
