package org.confluence.mod.client.handler;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.client.KeyBindings;
import org.confluence.mod.entity.hook.AbstractHookEntity;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.HookThrowingPacketC2S;
import org.confluence.mod.util.CuriosUtils;

public final class HookThrowingHandler {
    public static void handle(LocalPlayer localPlayer) {
        boolean isDown = false;
        while (KeyBindings.HOOK.get().consumeClick()) isDown = true;
        if (isDown) NetworkHandler.CHANNEL.sendToServer(HookThrowingPacketC2S.push());

        CuriosUtils.getSlot(localPlayer, "hook", 0).ifPresent(itemStack -> {
            CompoundTag tag = itemStack.getTag();
            if (tag != null && tag.get("hooks") instanceof ListTag list) {
                list.forEach(tag1 -> {
                    int id = ((CompoundTag) tag1).getInt("id");
                    if (localPlayer.level().getEntity(id) instanceof AbstractHookEntity hookEntity && hookEntity.getHookState() == AbstractHookEntity.HookState.HOOKED) {
                        if (localPlayer.input.jumping) {
                            NetworkHandler.CHANNEL.sendToServer(HookThrowingPacketC2S.pop(id));
                            return;
                        }
                        if (localPlayer.isCrouching()) return;
                        Vec3 subtract = hookEntity.position().subtract(localPlayer.position());
                        if (subtract.lengthSqr() < 1.0) {
                            Vec3 motion = localPlayer.getDeltaMovement().scale(0.05);
                            localPlayer.setDeltaMovement(motion.x, 0.0, motion.z);
                            PlayerJumpHandler.flushState(false);
                        } else {
                            Vec3 motion = subtract.normalize().scale(hookEntity.getPullVelocity());
                            localPlayer.setDeltaMovement(localPlayer.getDeltaMovement().scale(0.96).add(motion));
                        }
                    }
                });
            }
        });
    }
}
