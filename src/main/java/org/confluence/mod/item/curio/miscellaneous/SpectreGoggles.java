package org.confluence.mod.item.curio.miscellaneous;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.EchoBlockVisibilityPacketS2C;
import top.theillusivec4.curios.api.SlotContext;

public class SpectreGoggles extends BaseCurioItem {
    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        echo(slotContext.entity(), true);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        echo(slotContext.entity(), false);
    }

    private static void echo(LivingEntity living, boolean value) {
        if (living instanceof ServerPlayer serverPlayer) {
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new EchoBlockVisibilityPacketS2C(value)
            );
        }
    }
}
