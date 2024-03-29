package org.confluence.mod.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow
    protected int missTime;

    @Shadow
    @Nullable
    public LocalPlayer player;

    @Shadow
    @Nullable
    public HitResult hitResult;

    @Shadow
    @Nullable
    public MultiPlayerGameMode gameMode;

    @Inject(method = "continueAttack", at = @At("TAIL"))
    private void entityAttack(boolean flag, CallbackInfo ci) {
        if (player != null && missTime <= 0 && !player.isUsingItem()) {
            if (gameMode != null && flag && hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
                gameMode.attack(player, ((EntityHitResult) this.hitResult).getEntity());
                player.swing(InteractionHand.MAIN_HAND);
            }
        }
    }
}
