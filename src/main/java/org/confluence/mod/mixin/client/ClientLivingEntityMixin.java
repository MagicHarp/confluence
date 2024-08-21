package org.confluence.mod.mixin.client;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.client.handler.GravitationHandler;
import org.confluence.mod.client.handler.StepStoolHandler;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.FallDistancePacketC2S;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class ClientLivingEntityMixin {
    @Shadow public abstract EntityDimensions getDimensions(Pose pPose);

    @Inject(method = "checkFallDamage", at = @At("HEAD"))
    private void fall(double motionY, boolean onGround, BlockState blockState, BlockPos blockPos, CallbackInfo ci) {
        LivingEntity self = confluence$getSelf();
        if (motionY > 0.0 && self instanceof LocalPlayer && GravitationHandler.isShouldRot()) {
            self.fallDistance += motionY;
            NetworkHandler.CHANNEL.sendToServer(new FallDistancePacketC2S(self.fallDistance));
        }
    }

    @ModifyArg(method = "checkFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"), index = 2)
    private double fall2(double pPosY) {
        if (confluence$getSelf() instanceof Player player) {
            if (player.isLocalPlayer() && GravitationHandler.isShouldRot()) {
                return pPosY + getDimensions(player.getPose()).height;
            }
        }
        return pPosY;
    }

    @ModifyVariable(method = "travel", at = @At("HEAD"), argsOnly = true)
    private Vec3 confused(Vec3 vec3) {
        if (confluence$getSelf() instanceof LocalPlayer) {
            if (GravitationHandler.isShouldRot()) {
                return new Vec3(vec3.x * -1.0, vec3.y, vec3.z);
            } else if (StepStoolHandler.getStep() > 0) {
                return Vec3.ZERO;
            }
        }
        return vec3;
    }

    @Unique
    private LivingEntity confluence$getSelf() {
        return (LivingEntity) (Object) this;
    }
}
