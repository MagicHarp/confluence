package org.confluence.mod.mixin.integretion.shimmer;

import com.lowdragmc.shimmer.client.light.ColorPointLight;
import com.lowdragmc.shimmer.client.light.LightManager;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.client.shimmer.PlayerPointLight;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;

@Mixin(value = LightManager.class, remap = false)
public abstract class LightManagerMixin {
    @Inject(
        method = "updateNoUVLight",
        at = @At(value = "INVOKE", target = "Lcom/lowdragmc/shimmer/client/light/ColorPointLight;uploadBuffer(Ljava/nio/FloatBuffer;)V", ordinal = 0),
        locals = LocalCapture.CAPTURE_FAILSOFT)
    private void updatePlayerLight(CallbackInfo ci, LocalPlayer localPlayer, Vec3 localPlayerPosition, List<AbstractClientPlayer> players, float partialTicks, Iterator<AbstractClientPlayer> var5, AbstractClientPlayer player, Vec3 position, ColorPointLight light) {
        if (light instanceof PlayerPointLight) {
            light.setPos(
                (float) position.x,
                (float) position.y,
                (float) position.z
            );
        }
    }
}
