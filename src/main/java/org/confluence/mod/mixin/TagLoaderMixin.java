package org.confluence.mod.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagLoader;
import org.confluence.mod.item.curio.datadriven.DataDrivenCurioInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(TagLoader.class)
public abstract class TagLoaderMixin {
    @Shadow
    @Final
    private String directory;

    @Inject(method = "load", at = @At("RETURN"))
    private void addCustom(ResourceManager pResourceManager, CallbackInfoReturnable<Map<ResourceLocation, List<TagLoader.EntryWithSource>>> cir) {
        if ("tags/items".equals(directory)) {
            DataDrivenCurioInfo.bindTags(cir.getReturnValue());
        }
    }
}
