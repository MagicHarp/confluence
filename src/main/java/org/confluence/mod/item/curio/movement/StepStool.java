package org.confluence.mod.item.curio.movement;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.StepStoolStepPacketS2C;
import org.confluence.mod.util.CuriosUtils;
import top.theillusivec4.curios.api.SlotContext;

public class StepStool extends BaseCurioItem { // todo extraStep
    public StepStool(Rarity rarity) {
        super(rarity);
    }

    public StepStool() {}

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        super.onEquip(slotContext, prevStack, stack);
        send(slotContext, stack.getOrCreateTag().getInt("extraStep") + 1);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        send(slotContext, 0);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CuriosUtils.noSameCurio(slotContext.entity(), StepStool.class);
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
