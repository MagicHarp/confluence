package org.confluence.mod.mixin.client;

import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.renderer.AchievementToast;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.Map;

@Mixin(ClientAdvancements.class)
public abstract class ClientAdvancementsMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/Advancement;getDisplay()Lnet/minecraft/advancements/DisplayInfo;", ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void showAchievementToast(ClientboundUpdateAdvancementsPacket pPacket, CallbackInfo ci, Iterator<Map.Entry<ResourceLocation, AdvancementProgress>> var2, Map.Entry<ResourceLocation, AdvancementProgress> entry) {
        if (!entry.getValue().isDone()) return;
        if ("confluence:boots_of_the_hero".equals(entry.getKey().toString())) {
            minecraft.getToasts().addToast(new AchievementToast(
                    new ResourceLocation(Confluence.MODID, "textures/achievement/boots_of_the_hero.png"),
                    new DisplayInfo(
                            ItemStack.EMPTY,
                            Component.translatable("achievements.confluence.boots_of_the_hero.title"),
                            Component.translatable("achievements.confluence.boots_of_the_hero.description"),
                            null, FrameType.CHALLENGE, true, true, false
                    )));
        }
    }
}
