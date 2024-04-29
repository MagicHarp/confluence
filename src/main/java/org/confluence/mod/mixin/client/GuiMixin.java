package org.confluence.mod.mixin.client;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.capability.prefix.ItemPrefix;
import org.confluence.mod.capability.prefix.PrefixProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;
import java.util.function.UnaryOperator;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow
    protected ItemStack lastToolHighlight;

    @Redirect(method = "renderSelectedItemName(Lnet/minecraft/client/gui/GuiGraphics;I)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/MutableComponent;withStyle(Ljava/util/function/UnaryOperator;)Lnet/minecraft/network/chat/MutableComponent;"))
    private MutableComponent withPrefix(MutableComponent instance, UnaryOperator<Style> styleUnaryOperator) {
        MutableComponent component = instance.withStyle(lastToolHighlight.getRarity().getStyleModifier());
        Optional<ItemPrefix> optional = PrefixProvider.getPrefix(lastToolHighlight);
        if (optional.isEmpty()) return component;
        return Component.translatable("prefix.confluence." + optional.get().name)
            .withStyle(optional.get().tier >= 0 ? ChatFormatting.GREEN : ChatFormatting.RED)
            .append(" ").append(component);
    }
}
