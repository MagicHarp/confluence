package org.confluence.mod.client.handler;

import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.confluence.mod.client.KeyBindings;
import org.confluence.mod.entity.hook.AbstractHookEntity;
import org.confluence.mod.item.hook.Hooks;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.HookThrowingPacketC2S;
import org.confluence.mod.util.CuriosUtils;

import java.util.Iterator;

@OnlyIn(Dist.CLIENT)
public final class HookThrowingHandler {
    public static void handle(LocalPlayer localPlayer) {
        boolean isDown = false;
        while (KeyBindings.HOOK.get().consumeClick()) isDown = true;
        if (isDown) NetworkHandler.CHANNEL.sendToServer(HookThrowingPacketC2S.push());

        CuriosUtils.getSlot(localPlayer, "hook", 0).ifPresent(itemStack -> {
            CompoundTag tag = itemStack.getTag();
            if (tag != null && tag.get("hooks") instanceof ListTag list) {
                Iterator<Tag> iterator = list.iterator();
                while (iterator.hasNext()) {
                    int id = ((CompoundTag) iterator.next()).getInt("id");
                    Entity entity = localPlayer.level().getEntity(id);
                    if (entity == null) {
                        iterator.remove();
                    }
                    if (entity instanceof AbstractHookEntity hookEntity && hookEntity.getHookState() == AbstractHookEntity.HookState.HOOKED) {
                        Input input = localPlayer.input;
                        if (input.jumping) {
                            NetworkHandler.CHANNEL.sendToServer(HookThrowingPacketC2S.pop(id));
                            return;
                        }
                        if (localPlayer.isCrouching()) return;

                        Vec3 subtract = hookEntity.position().subtract(localPlayer.position());
                        if (itemStack.getItem() == Hooks.ANTI_GRAVITY_HOOK.get()) {
                            float ry = localPlayer.getYRot() * Mth.DEG_TO_RAD;
                            float cos = Mth.cos(ry);
                            float sin = Mth.sin(ry);
                            Vec2 vector = input.getMoveVector();
                            double vx = vector.x * cos + vector.y * -sin;
                            double vy = -Mth.sin(localPlayer.getXRot() * Mth.DEG_TO_RAD) * input.forwardImpulse;
                            double vz = vector.x * sin + vector.y * cos;
                            double d = Math.sqrt(vx * vx + vy * vy + vz * vz);
                            if (d == 0.0) {
                                localPlayer.setDeltaMovement(Vec3.ZERO);
                            } else {
                                localPlayer.setDeltaMovement(vx / d * 0.5, vy / d * 0.5, vz / d * 0.5);
                            }
                            PlayerJumpHandler.flushState(false);
                        } else {
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
                }
            }
        });
    }
}
