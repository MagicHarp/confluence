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

public class BlizzardInABalloon extends BaseCurioItem implements IOneTimeJump, IJumpBoost {
    @Override
    public double getBoost() {
        return 1.3;
    }

    @Override
    public int getJumpTicks() {
        return 16;
    }

    @Override
    public double getJumpSpeed() {
        return 0.4;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        if (living instanceof ServerPlayer serverPlayer) {
            IOneTimeJump.sendMaxJump(serverPlayer);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        if (living instanceof ServerPlayer serverPlayer) {
            IOneTimeJump.sendMaxJump(serverPlayer);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IOneTimeJump.TOOLTIP);
        list.add(IJumpBoost.TOOLTIP);
    }
}
