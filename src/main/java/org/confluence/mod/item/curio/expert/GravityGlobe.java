package org.confluence.mod.item.curio.expert;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.GravityGlobePacketS2C;
import top.theillusivec4.curios.api.SlotContext;

public class GravityGlobe extends BaseCurioItem implements ModRarity.Expert {
    public GravityGlobe() {
        super(ModRarity.EXPERT);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        super.onEquip(slotContext, prevStack, stack);
        sendMsg(slotContext.entity(), true);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        sendMsg(slotContext.entity(), false);
    }

    private static void sendMsg(LivingEntity living, boolean has) {
        if (living instanceof ServerPlayer serverPlayer) {
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new GravityGlobePacketS2C(has)
            );
        }
    }
}
