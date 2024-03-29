package org.confluence.mod.item.curio.movement;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import top.theillusivec4.curios.api.SlotContext;

public class SandstormInABottle extends BaseCurioItem implements IOneTimeJump {
    @Override
    public int getJumpTicks() {
        return 20;
    }

    @Override
    public double getJumpSpeed() {
        return 0.45;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
            IOneTimeJump.sendMaxJump(serverPlayer);
        }
    }
}
