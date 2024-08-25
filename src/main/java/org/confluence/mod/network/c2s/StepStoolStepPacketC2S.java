package org.confluence.mod.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.entity.StepStoolEntity;
import org.confluence.mod.item.curio.movement.StepStool;
import org.confluence.mod.util.CuriosUtils;

import java.util.function.Supplier;

public record StepStoolStepPacketC2S(int slot, int step, boolean increase) {
    public static void encode(StepStoolStepPacketC2S packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(packet.slot);
        friendlyByteBuf.writeInt(packet.step);
        friendlyByteBuf.writeBoolean(packet.increase);
    }

    public static StepStoolStepPacketC2S decode(FriendlyByteBuf friendlyByteBuf) {
        return new StepStoolStepPacketC2S(friendlyByteBuf.readInt(), friendlyByteBuf.readInt(), friendlyByteBuf.readBoolean());
    }

    public static void handle(StepStoolStepPacketC2S packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer == null || packet.slot == -1) return;
            if (packet.step == 1 && packet.increase) {
                StepStoolEntity pEntity = new StepStoolEntity(serverPlayer);
                serverPlayer.level().addFreshEntity(pEntity);
                CuriosUtils.getSlot(serverPlayer, "accessory", packet.slot).ifPresent(itemStack -> {
                    if (itemStack.getItem() instanceof StepStool) {
                        itemStack.getOrCreateTag().putInt("id", pEntity.getId());
                    }
                });
            } else {
                CuriosUtils.getSlot(serverPlayer, "accessory", packet.slot).ifPresent(itemStack -> {
                    if (itemStack.getItem() instanceof StepStool) {
                        int id = itemStack.getOrCreateTag().getInt("id");
                        Entity entity = serverPlayer.level().getEntity(id);
                        if (entity instanceof StepStoolEntity stepStool) {
                            if (packet.step == 0) {
                                stepStool.setOwner(null);
                            } else {
                                stepStool.setStep(packet.step);
                            }
                        }
                    }
                });
            }
        });
        context.setPacketHandled(true);
    }
}
