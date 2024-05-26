package org.confluence.mod.network.c2s;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.entity.hook.AbstractHookEntity;
import org.confluence.mod.item.hook.AbstractHookItem;
import org.confluence.mod.util.CuriosUtils;

import java.util.function.Supplier;

public record HookThrowingPacketC2S(boolean throwing, int id) {
    public static HookThrowingPacketC2S push() {
        return new HookThrowingPacketC2S(true, 0);
    }

    public static HookThrowingPacketC2S pop(int id) {
        return new HookThrowingPacketC2S(false, id);
    }

    public static void encode(HookThrowingPacketC2S packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.throwing);
        friendlyByteBuf.writeInt(packet.id);
    }

    public static HookThrowingPacketC2S decode(FriendlyByteBuf friendlyByteBuf) {
        return new HookThrowingPacketC2S(friendlyByteBuf.readBoolean(), friendlyByteBuf.readInt());
    }

    public static void handle(HookThrowingPacketC2S packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            ServerLevel level = player.serverLevel();
            if (packet.throwing) {
                CuriosUtils.getSlot(player, "hook", 0).ifPresent(itemStack -> {
                    if (itemStack.getItem() instanceof AbstractHookItem item && item.canHook(level, itemStack)) {
                        AbstractHookEntity hook = item.getHook(itemStack, item, player, level);
                        hook.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, item.getHookVelocity(), 1.0F);
                        level.addFreshEntity(hook);
                        CompoundTag tag = new CompoundTag();
                        tag.putInt("id", hook.getId());
                        if (itemStack.getOrCreateTag().get("hooks") instanceof ListTag list) {
                            AbstractHookItem.HookType hookType = item.getHookType();
                            if (hookType == AbstractHookItem.HookType.SINGLE) {
                                AbstractHookItem.removeAll(list, level);
                            } else if (hookType == AbstractHookItem.HookType.SIMULTANEOUS && list.size() == item.getHookAmount()) {
                                AbstractHookEntity hookEntity = AbstractHookItem.getHookEntity(list.get(0), level);
                                if (hookEntity != null) hookEntity.setHookState(AbstractHookEntity.HookState.POP);
                            }
                            list.add(tag);
                        }
                    }
                });
            } else {
                Entity entity = level.getEntity(packet.id);
                if (entity instanceof AbstractHookEntity hookEntity) {
                    hookEntity.setHookState(AbstractHookEntity.HookState.POP);
                }
            }
        });
        context.setPacketHandled(true);
    }
}
