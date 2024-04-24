package org.confluence.mod.item.curio.miscellaneous;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.capability.prefix.PrefixProvider;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.EchoBlockVisibilityPacketS2C;
import top.theillusivec4.curios.api.SlotContext;

public class SpectreGoggles extends BaseCurioItem {
    public SpectreGoggles() {
        super(ModRarity.PINK);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        echo(living, true);
        PrefixProvider.getPrefix(stack).ifPresent(itemPrefix -> itemPrefix.applyPrefix(living));
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        echo(living, false);
        PrefixProvider.getPrefix(stack).ifPresent(itemPrefix -> itemPrefix.expirePrefix(living));
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
