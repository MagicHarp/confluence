package org.confluence.mod.mixin.entity;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.extensions.IForgeLivingEntity;
import net.minecraftforge.fluids.FluidType;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.confluence.mod.effect.beneficial.GravitationEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin implements IForgeLivingEntity {
    @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;onGround()Z", ordinal = 0))
    private boolean notCheckOnGround(LocalPlayer instance) {
        return ClientPacketHandler.isHasCthulhu() || instance.onGround();
    }

    @Override
    public void sinkInFluid(FluidType type) {
        Vec3 motion = self().getDeltaMovement();
        double factor = GravitationEffect.isShouldRot() ? 0.04 : -0.04;
        self().setDeltaMovement(motion.x, motion.y + factor * self().getAttributeValue(ForgeMod.SWIM_SPEED.get()), motion.z);
    }
}
