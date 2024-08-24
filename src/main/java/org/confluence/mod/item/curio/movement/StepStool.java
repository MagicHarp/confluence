package org.confluence.mod.item.curio.movement;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.entity.StepStoolEntity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.StepStoolStepPacketS2C;
import org.confluence.mod.util.CuriosUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class StepStool extends BaseCurioItem {
    public static final Component TOOLTIP = Component.translatable("item.confluence.step_stool.tooltip");

    public StepStool(Rarity rarity) {
        super(rarity);
    }

    public StepStool() {}

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (prevStack.getItem() == stack.getItem()) return;
        super.onEquip(slotContext, prevStack, stack);
        send(slotContext, stack.getOrCreateTag().getInt("extraStep") + 1);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (newStack.getItem() == stack.getItem()) return;
        super.onUnequip(slotContext, newStack, stack);
        send(slotContext, 0);
        Level level = slotContext.entity().level();
        if (!level.isClientSide && level.getEntity(stack.getOrCreateTag().getInt("id")) instanceof StepStoolEntity stepStool) {
            stepStool.setOwner(null);
        }
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CuriosUtils.noSameCurio(slotContext.entity(), StepStool.class);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(TOOLTIP);
        if (itemStack.getTag() != null) {
            list.add(Component.translatable("item.confluence.step_stool.tooltip2", itemStack.getTag().getInt("extraStep"))
                .withStyle(style -> style.withColor(ChatFormatting.BLUE)));
        }
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.step_stool.info")
        };
    }

    public static void send(SlotContext slotContext, int maxStep) {
        if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new StepStoolStepPacketS2C(slotContext.index(), maxStep)
            );
        }
    }
}
