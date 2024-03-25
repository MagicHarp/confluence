package org.confluence.mod.item.curio.informational;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.network.MechanicalBlockVisibilityPacketS2C;
import org.confluence.mod.network.NetworkHandler;
import top.theillusivec4.curios.api.SlotContext;

public class MechanicalLens extends BaseCurioItem {
    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        mechanical(slotContext.entity(), true);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        mechanical(slotContext.entity(), false);
    }

    private static void mechanical(LivingEntity living, boolean value) {
        if (living instanceof ServerPlayer serverPlayer) {
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new MechanicalBlockVisibilityPacketS2C(value)
            );
        }
    }
}
