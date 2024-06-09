package org.confluence.mod.mixin.integretion.bettercombat;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.bettercombat.api.WeaponAttributes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.sword.LightSaber;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets = "net.bettercombat.network.ServerNetwork", remap = false)
public abstract class ServerNetworkMixin {
    @WrapWithCondition(method = "lambda$initializeHandlers$5", at = @At(value = "INVOKE", target = "Lnet/bettercombat/utils/SoundHelper;playSound(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;Lnet/bettercombat/api/WeaponAttributes$Sound;)V"))
    private static boolean ifPlaySound(ServerLevel world, Entity entity, WeaponAttributes.Sound sound) {
        if (sound.id().contains("confluence:lightsaber") && entity instanceof LivingEntity living) {
            ItemStack itemInHand = living.getItemInHand(InteractionHand.MAIN_HAND);
            return !(itemInHand.getItem() instanceof LightSaber) || !LightSaber.isTurnOff(itemInHand);
        }
        return true;
    }
}
