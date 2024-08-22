package org.confluence.mod.client.handler;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.KeyBindings;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.StepStoolStepPacketC2S;
import org.confluence.mod.network.s2c.StepStoolStepPacketS2C;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public final class StepStoolHandler {
    private static final Vec3 UP = new Vec3(0.0, 1.0000001, 0.0);
    private static final Vec3 DOWN = new Vec3(0.0, -1.0000001, 0.0);
    private static boolean upKeyDown = false;
    private static boolean shiftKeyDown = false;
    private static int step = 0;
    private static int maxStep = 0;
    private static int slot = StepStoolStepPacketS2C.NO_CURIO;

    public static void handle(LocalPlayer localPlayer) {
        if (localPlayer == null) {
            step = 0;
            maxStep = 0;
            slot = StepStoolStepPacketS2C.NO_CURIO;
            return;
        } else if (slot == StepStoolStepPacketS2C.NO_CURIO || (step == 0 && !localPlayer.onGround())) {
            return;
        }

        if (step > 0) {
            if (localPlayer.input.jumping) {
                localPlayer.jumpFromGround();
                setStep(0);
                return;
            } else if (localPlayer.getVehicle() != null) {
                setStep(0);
                return;
            }
        }

        if (KeyBindings.STEP_STOOL.get().isDown()) {
            if (!upKeyDown && step < maxStep) {
                localPlayer.move(MoverType.SELF, UP);
                setStep(step + 1);
                upKeyDown = true;
            }
        } else {
            upKeyDown = false;
        }

        if (!upKeyDown && localPlayer.isShiftKeyDown()) {
            if (!shiftKeyDown && step > 0) {
                localPlayer.move(MoverType.SELF, DOWN);
                setStep(step - 1);
                shiftKeyDown = true;
            }
        } else {
            shiftKeyDown = false;
        }

        if (step > 0) {
            localPlayer.setDeltaMovement(new Vec3(0.0, localPlayer.getDeltaMovement().y, 0.0));
        }
    }

    public static void setStep(int step) {
        StepStoolHandler.step = step;
        NetworkHandler.CHANNEL.sendToServer(new StepStoolStepPacketC2S(slot, step));
    }

    public static int getStep() {
        return step;
    }

    public static boolean onStool() {
        return step > 0;
    }

    public static void handlePacket(StepStoolStepPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            if (packet.slot() == StepStoolStepPacketS2C.RESET_STEP) {
                step = 0;
            } else {
                maxStep = packet.maxStep();
                slot = maxStep == 0 ? StepStoolStepPacketS2C.NO_CURIO : packet.slot();
            }
        });
        context.setPacketHandled(true);
    }
}
