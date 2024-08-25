package org.confluence.mod.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundCooldownPacket;
import org.confluence.mod.item.sword.ISwordProjectile;
import org.confluence.mod.misc.ModSoundEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "handleItemCooldown", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemCooldowns;removeCooldown(Lnet/minecraft/world/item/Item;)V"))
    private void playSound(ClientboundCooldownPacket pPacket, CallbackInfo ci) {
        if (pPacket.getItem() instanceof ISwordProjectile) {
            assert minecraft.player != null;
            minecraft.player.playSound(ModSoundEvents.COOLDOWN_RECOVERY.get());
        }
    }
}
