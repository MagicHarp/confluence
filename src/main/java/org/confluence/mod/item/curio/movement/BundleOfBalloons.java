package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.capability.prefix.PrefixProvider;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.PlayerJumpPacketS2C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class BundleOfBalloons extends BaseCurioItem implements IJumpBoost {
    public BundleOfBalloons() {
        super(ModRarity.YELLOW);
    }

    @Override
    public double getBoost() {
        return 1.33;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        super.onEquip(slotContext, prevStack, stack);
        if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new PlayerJumpPacketS2C(
                    -2.0F,
                    SandstormInABottle.SPEED,
                    SandstormInABottle.TICKS,
                    BlizzardInABottle.SPEED,
                    BlizzardInABottle.TICKS,
                    -2.0F,
                    CloudInABalloon.SPEED
                )
            );
            PrefixProvider.getPrefix(stack).ifPresent(itemPrefix -> itemPrefix.applyCurioPrefix(serverPlayer));
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
            IMultiJump.sendMsg(serverPlayer);
            PrefixProvider.getPrefix(stack).ifPresent(itemPrefix -> itemPrefix.expireCurioPrefix(serverPlayer));
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(IJumpBoost.TOOLTIP);
    }
}
