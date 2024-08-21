package org.confluence.mod.client.handler;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.KeyBindings;
import org.confluence.mod.network.s2c.StepStoolPacketS2C;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public final class StepStoolHandler {
    private static final Vec3 UP = new Vec3(0.0, 1.0000001, 0.0);
    private static final Vec3 DOWN = new Vec3(0.0, -1.0000001, 0.0);
    private static boolean upKeyDown = false;
    private static boolean shiftKeyDown = false;
    private static int step = 0;
    private static int maxStep = 0;

    public static void handle(LocalPlayer localPlayer) {
        if (maxStep == 0 || (step == 0 && !localPlayer.onGround())) return;

        if (step > 0 && localPlayer.input.jumping) {
            localPlayer.jumpFromGround();
            step = 0;
            return;
        }

        if (KeyBindings.STEP_STOOL.get().isDown()) {
            if (!upKeyDown && step < maxStep) {
                localPlayer.move(MoverType.SELF, UP);
                step++;
                upKeyDown = true;
            }
        } else {
            upKeyDown = false;
        }

        if (!upKeyDown && localPlayer.isShiftKeyDown()) {
            if (!shiftKeyDown && step > 0) {
                localPlayer.move(MoverType.SELF, DOWN);
                step--;
                shiftKeyDown = true;
            }
        } else {
            shiftKeyDown = false;
        }

        if (step > 0) {
            localPlayer.setDeltaMovement(Vec3.ZERO);
        }
    }

    public static int getStep() {
        return step;
    }

    public static void handlePacket(StepStoolPacketS2C packet, Supplier<NetworkEvent.Context> ctx){
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> maxStep = packet.maxStep());
        context.setPacketHandled(true);
    }
}
