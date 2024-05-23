package org.confluence.mod.client.handler;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
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
                    if (tag1 instanceof CompoundTag tag2) {
                        int id = tag2.getInt("id");
                        if (localPlayer.level().getEntity(id) instanceof AbstractHookEntity hookEntity && hookEntity.getHookState() == AbstractHookEntity.HookState.HOOKED) {
                            if (localPlayer.input.jumping) {
                                NetworkHandler.CHANNEL.sendToServer(HookThrowingPacketC2S.pop(id));
                            } else {
                                double gravity = localPlayer.getAttributeValue(ForgeMod.ENTITY_GRAVITY.get());
                                Vec3 vec3 = hookEntity.position().subtract(localPlayer.getX(), localPlayer.getY(), localPlayer.getZ()).normalize().scale(0.1).add(0.0, -gravity, 0.0);
                                localPlayer.addDeltaMovement(vec3);
                            }
                        }
                    }
                });
            }
        });
    }
}
