package org.confluence.mod.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import org.confluence.mod.item.gun.BaseGunItem;

@OnlyIn(Dist.CLIENT)
public final class GunShootingHandler {
    public static void handle(InputEvent.InteractionKeyMappingTriggered event, LocalPlayer localPlayer) {
        if (event.isUseItem()) return;
        ItemStack itemStack = localPlayer.getMainHandItem();
        if (itemStack.getItem() instanceof BaseGunItem baseGunItem) {
            event.setSwingHand(false);
            event.setCanceled(true);

            if (!baseGunItem.isAuto()) {
                Minecraft.getInstance().options.keyAttack.setDown(false);
            }
        }
    }
}
