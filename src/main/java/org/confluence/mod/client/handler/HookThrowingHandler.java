package org.confluence.mod.client.handler;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.confluence.mod.client.KeyBindings;
import org.confluence.mod.entity.hook.AbstractHookEntity;
import org.confluence.mod.item.hook.AntiGravityHookItem;
import org.confluence.mod.item.hook.Hooks;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.HookThrowingPacketC2S;
import org.confluence.mod.util.CuriosUtils;

@OnlyIn(Dist.CLIENT)
public final class HookThrowingHandler {
    private static final Vec3 LEFT_VEC = new Vec3(0.0, -0.008, 0.0);
    private static final Vec3 RIGHT_VEC = new Vec3(0.0, 0.008, 0.0);
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
                        if (localPlayer.input.jumping && localPlayer.input.shiftKeyDown) {
                            NetworkHandler.CHANNEL.sendToServer(HookThrowingPacketC2S.pop(id));
                            return;
                        }

                        Vec3 subtract = hookEntity.position().subtract(localPlayer.position());
                        if (itemStack.getItem() == Hooks.ANTI_GRAVITY_HOOK.get()) {
                            Vec3 deltaMovement = localPlayer.getDeltaMovement();
                            if (localPlayer.input.up) {
                                localPlayer.setDeltaMovement(deltaMovement.add(Vec3.directionFromRotation(localPlayer.getXRot(), localPlayer.getYRot()).scale(0.05)));
                            }
                            if (localPlayer.input.leftImpulse != 0.0) {
                                localPlayer.setDeltaMovement(deltaMovement.add(subtract.cross(localPlayer.input.left ? LEFT_VEC : RIGHT_VEC)));

                            }
                            if (localPlayer.input.jumping) {
                                localPlayer.setDeltaMovement(deltaMovement.add(0, 0.2, 0));
                            }
                            if (localPlayer.input.shiftKeyDown) {
                                localPlayer.setDeltaMovement(deltaMovement.add(0, -0.3, 0));
                            }
                        } else {
                            if (localPlayer.isCrouching()) return;
                            if (subtract.lengthSqr() < 1.0) {
                                Vec3 motion = localPlayer.getDeltaMovement().scale(0.05);
                                localPlayer.setDeltaMovement(motion.x, 0.0, motion.z);
                                PlayerJumpHandler.flushState(false);
                            } else {
                                Vec3 motion = subtract.normalize().scale(hookEntity.getPullVelocity());
                                localPlayer.setDeltaMovement(localPlayer.getDeltaMovement().scale(0.96).add(motion));
                            }
                        }
                    }
                });
            }
        });
    }
}
