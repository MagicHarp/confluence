package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModConfigs;
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
        return ModConfigs.BUNDLE_OF_BALLOONS_JUMP_BOOST.get();
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        super.onEquip(slotContext, prevStack, stack);
        if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new PlayerJumpPacketS2C(
                    -2.0,
                    ModConfigs.SANDSTORM_IN_A_BALLOON_JUMP_SPEED.get(),
                    ModConfigs.SANDSTORM_IN_A_BALLOON_JUMP_TICKS.get(),
                    ModConfigs.BLIZZARD_IN_A_BALLOON_JUMP_SPEED.get(),
                    ModConfigs.BLIZZARD_IN_A_BALLOON_JUMP_TICKS.get(),
                    -2.0,
                    ModConfigs.CLOUD_IN_A_BALLOON_JUMP_SPEED.get()
                )
            );
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
            IMultiJump.sendMsg(serverPlayer);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(IJumpBoost.TOOLTIP);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.bundle_of_balloons.info"),
            Component.translatable("item.confluence.bundle_of_balloons.info2")
        };
    }
}
