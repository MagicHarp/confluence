package org.confluence.mod.mixin.client;

import net.minecraft.advancements.Advancement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.advancements.AdvancementWidget;
import org.confluence.mod.advancement.ModAchievements;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AdvancementWidget.class)
public abstract class AdvancementWidgetMixin {
    @Shadow
    @Final
    private Advancement advancement;

    @Inject(method = "drawConnectivity", at = @At("HEAD"), cancellable = true)
    private void disconnect(GuiGraphics pGuiGraphics, int pX, int pY, boolean pDropShadow, CallbackInfo ci) {
        if (ModAchievements.DISPLAY_OFFSET.containsKey(advancement.getId())) {
            ci.cancel();
        }
    }
}
