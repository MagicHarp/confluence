package org.confluence.mod.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.capability.prefix.ItemPrefix;
import org.confluence.mod.capability.prefix.PrefixProvider;
import org.confluence.mod.client.ClientConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Optional;
import java.util.function.UnaryOperator;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow
    protected ItemStack lastToolHighlight;

    @ModifyReceiver(method = "renderSelectedItemName(Lnet/minecraft/client/gui/GuiGraphics;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/MutableComponent;withStyle(Ljava/util/function/UnaryOperator;)Lnet/minecraft/network/chat/MutableComponent;"))
    private MutableComponent withPrefix(MutableComponent instance, UnaryOperator<Style> pModifyFunc) {
        MutableComponent component = instance.withStyle(lastToolHighlight.getRarity().getStyleModifier());
        Optional<ItemPrefix> optional = PrefixProvider.getPrefix(lastToolHighlight);
        return optional.map(itemPrefix -> Component.translatable("prefix.confluence." + itemPrefix.name)
                .withStyle(itemPrefix.tier >= 0 ? ChatFormatting.GREEN : ChatFormatting.RED)
                .append(" ").append(component)).orElse(component);
    }

    @ModifyVariable(method = "renderEffects", at = @At(value = "STORE", ordinal = 0/* first */), ordinal = 2/* ISTORE 12 */)
    private int modify0(int i) {
        if(ClientConfigs.leftEffectIcon) return 25;
        return i;
    }

    @ModifyVariable(method = "renderEffects", at = @At(value = "STORE", ordinal = 1/* second */), ordinal = 2/* ISTORE 12 */)
    private int modify1(int i) {
        if (ClientConfigs.leftEffectIcon) return i * -1;
        return i;
    }

    @ModifyVariable(method = "renderEffects", at = @At(value = "STORE", ordinal = 2/* third */), ordinal = 2/* ISTORE 12 */)
    private int modify2(int i) {
        if (ClientConfigs.leftEffectIcon) return i * -1;
        return i;
    }
}
