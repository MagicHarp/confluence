package org.confluence.mod.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.item.curio.movement.StepStool;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public record StepStoolNBTPacketC2S(int value) {
    public static void encode(StepStoolNBTPacketC2S packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(packet.value);
    }

    public static StepStoolNBTPacketC2S decode(FriendlyByteBuf friendlyByteBuf) {
        return new StepStoolNBTPacketC2S(friendlyByteBuf.readInt());
    }

    public static void handle(StepStoolNBTPacketC2S packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer == null) return;
            AtomicReference<ItemStack> atomic = new AtomicReference<>();
            CuriosApi.getCuriosInventory(serverPlayer).ifPresent(handler -> {
                Map<String, ICurioStacksHandler> curios = handler.getCurios();
                ICurioStacksHandler stacksHandler = curios.get("accessory");
                if (stacksHandler != null) {
                    IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); i++) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && stack.getItem() instanceof StepStool) {
                            atomic.set(stack);
                            return;
                        }
                    }
                }
            });
            if (atomic.get() != null) {
                atomic.get().getOrCreateTag().putInt("step", packet.value);
            }
        });
        context.setPacketHandled(true);
    }
}
