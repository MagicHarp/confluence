package org.confluence.mod.mixin.client;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.capability.prefix.ItemPrefix;
import org.confluence.mod.capability.prefix.PrefixProvider;
import org.confluence.mod.client.ClientConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Optional;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow
    protected ItemStack lastToolHighlight;

    @ModifyArg(method = "renderSelectedItemName(Lnet/minecraft/client/gui/GuiGraphics;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getHighlightTip(Lnet/minecraft/network/chat/Component;)Lnet/minecraft/network/chat/Component;"), remap = false)
    private Component withPrefix(Component component) {
        Optional<ItemPrefix> optional = PrefixProvider.getPrefix(lastToolHighlight);
        return optional.map(itemPrefix -> (Component) Component.translatable("prefix.confluence." + itemPrefix.name)
                .withStyle(itemPrefix.tier >= 0 ? ChatFormatting.GREEN : ChatFormatting.RED)
                .append(" ").append(component)
        ).orElse(component);
    }

    @ModifyVariable(method = "renderEffects", at = @At(value = "STORE", ordinal = 0/* first */), ordinal = 2/* ISTORE 12 */)
    private int modify0(int i) {
        return ClientConfigs.leftEffectIcon ? 25 : i;
    }

    @ModifyVariable(method = "renderEffects", at = @At(value = "STORE", ordinal = 1/* second */), ordinal = 2/* ISTORE 12 */)
    private int modify1(int i) {
        return ClientConfigs.leftEffectIcon ? -i : i;
    }

    @ModifyVariable(method = "renderEffects", at = @At(value = "STORE", ordinal = 2/* third */), ordinal = 2/* ISTORE 12 */)
    private int modify2(int i) {
        return ClientConfigs.leftEffectIcon ? -i : i;
    }
}
