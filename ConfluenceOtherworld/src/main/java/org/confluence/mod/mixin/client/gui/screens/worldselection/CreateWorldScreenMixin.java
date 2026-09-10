package org.confluence.mod.mixin.client.gui.screens.worldselection;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.layouts.LayoutSettings;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldCreationContext;
import org.confluence.mod.client.gui.SecretSeedsSelectionScreen;
import org.confluence.mod.mixed.IWorldOptions;
import org.mesdag.portlib.client.gui.components.PortImageButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {
    @Inject(method = "createFromExisting", at = @At("HEAD"))
    private static void resetWorldOptions(CallbackInfoReturnable<CreateWorldScreen> cir, @Local(argsOnly = true) WorldCreationContext settings) {
        IWorldOptions.of(settings.options()).confluence$resetSecretFlag();
    }

    @Mixin(targets = "net.minecraft.client.gui.screens.worldselection.CreateWorldScreen$WorldTab")
    public abstract static class WorldTabMixin {
        @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/GridLayout$RowHelper;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;Lnet/minecraft/client/gui/layouts/LayoutSettings;)Lnet/minecraft/client/gui/layouts/LayoutElement;"))
        private <T extends LayoutElement> T setSeedEditorWidthAndAddButton(GridLayout.RowHelper instance, T child, LayoutSettings layoutSettings, Operation<T> original, CreateWorldScreen this$0) {
            EditBox seedEdit = (EditBox) child;
            seedEdit.setWidth(seedEdit.getWidth() - seedEdit.getHeight() - 2);
            GridLayout layout = new GridLayout(0, 0).columnSpacing(8);
            layout.addChild(child, 0, 0, layoutSettings);
            layout.addChild(new PortImageButton(0, 0, 20, 20, SecretSeedsSelectionScreen.SPRITES, button -> {
                button.setFocused(false);
                this$0.getMinecraft().pushGuiLayer(new SecretSeedsSelectionScreen(seedEdit, this$0.getUiState()));
            }), 0, 1, LayoutSettings.defaults().paddingLeft(-4));
            original.call(instance, layout, layoutSettings);
            return child;
        }
    }
}
