package org.confluence.mod.item.curio.movement;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import top.theillusivec4.curios.api.SlotContext;

public class CloudInABottle extends BaseCurioItem implements IMultiJump {
    @Override
    public int getJumpTimes() {
        return 1;
    }

    @Override
    public double getMultiY() {
        return 1.0;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(slotContext.entity() instanceof ServerPlayer serverPlayer) {
            IMultiJump.sendMaxJump(serverPlayer);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity() instanceof ServerPlayer serverPlayer) {
            IMultiJump.sendMaxJump(serverPlayer);
        }
    }
}
