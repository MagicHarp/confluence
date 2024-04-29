package org.confluence.mod.mixin.client;

import net.minecraft.client.player.LocalPlayer;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {
    @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;onGround()Z", ordinal = 0))
    private boolean notCheckOnGround(LocalPlayer instance) {
        return ClientPacketHandler.isHasCthulhu() || instance.onGround();
    }
}
