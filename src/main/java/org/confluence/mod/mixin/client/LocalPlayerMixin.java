package org.confluence.mod.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.extensions.IForgeLivingEntity;
import net.minecraftforge.fluids.FluidType;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.confluence.mod.client.handler.GravitationHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin implements IForgeLivingEntity {
    @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;onGround()Z", ordinal = 0))
    private boolean notCheckOnGround(boolean original) {
        return original || ClientPacketHandler.isHasCthulhu();
    }

    @Override
    public void sinkInFluid(FluidType type) {
        Vec3 motion = self().getDeltaMovement();
        double factor = GravitationHandler.isShouldRot() ? 0.04 : -0.04;
        self().setDeltaMovement(motion.x, motion.y + factor * self().getAttributeValue(ForgeMod.SWIM_SPEED.get()), motion.z);
    }
}
