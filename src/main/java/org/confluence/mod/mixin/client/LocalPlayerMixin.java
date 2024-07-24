package org.confluence.mod.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.extensions.IForgeLivingEntity;
import net.minecraftforge.fluids.FluidType;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.confluence.mod.client.handler.GravitationHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer implements IForgeLivingEntity {
    private LocalPlayerMixin(ClientLevel pClientLevel, GameProfile pGameProfile){
        super(pClientLevel, pGameProfile);
    }

    @Shadow
    public abstract boolean isUsingItem();

    @Shadow public Input input;

    @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;onGround()Z", ordinal = 0))
    private boolean notCheckOnGround(boolean original){
        return original || ClientPacketHandler.isHasCthulhu();
    }

    @Override
    public void sinkInFluid(FluidType type){
        Vec3 motion = self().getDeltaMovement();
        double factor = GravitationHandler.isShouldRot() ? 0.04 : -0.04;
        self().setDeltaMovement(motion.x, motion.y + factor * self().getAttributeValue(ForgeMod.SWIM_SPEED.get()), motion.z);
    }

    @Inject(method = "aiStep", at = @At(value = "INVOKE",target = "Lnet/minecraft/client/player/Input;tick(ZF)V",shift = At.Shift.AFTER))
    private void aiStep(CallbackInfo ci){
        if(isUsingItem() && !isPassenger()){
            this.input.leftImpulse *= 5;
            this.input.forwardImpulse *= 5;
        }
    }
}
