package org.confluence.mod.item.curio.movement;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import top.theillusivec4.curios.api.SlotContext;

public class RocketBoots extends BaseCurioItem implements IMayFly {
    @Override
    public int getFlyTicks() {
        return 32;
    }

    @Override
    public double getFlySpeed() {
        return 0.3;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
            IMayFly.sendMaxFly(serverPlayer);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
            IMayFly.sendMaxFly(serverPlayer);
        }
    }
}
