package org.confluence.mod.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.confluence.mod.client.handler.GravitationHandler;
import org.confluence.mod.misc.ModTags;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @ModifyArg(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"), index = 0)
    private double reverseX(double x) {
        return GravitationHandler.isShouldRot() ? x * -1.0 : x;
    }

    @ModifyArg(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"), index = 1)
    private double reverseY(double y) {
        return GravitationHandler.isShouldRot() ? y * -1.0 : y;
    }

    @ModifyExpressionValue(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isScoping()Z"))
    private boolean hasScope(boolean original) {
        LocalPlayer player = minecraft.player;
        if (player == null) return original;
        return original || (ClientPacketHandler.isHasScope() && player.isCrouching() && player.getItemInHand(InteractionHand.MAIN_HAND).is(ModTags.RANGED_WEAPON));
    }
}
