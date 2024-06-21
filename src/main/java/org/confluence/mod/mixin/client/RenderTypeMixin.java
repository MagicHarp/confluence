package org.confluence.mod.mixin.client;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.client.ModRenderTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static org.confluence.mod.Confluence.MODID;

@Mixin(value = RenderType.class, priority = 10000)
public class RenderTypeMixin {
    @Unique
    private static List<RenderType> confluence$renderTypes;

    @Inject(method = "chunkBufferLayers", at = @At("RETURN"), cancellable = true)
    private static void put(CallbackInfoReturnable<List<RenderType>> cir) {
        if (ModRenderTypes.shimmerStillDynamic == null) {
            ModRenderTypes.shimmerStillDynamic = ModRenderTypes.getBlockDynamic(
                new ResourceLocation(MODID, "textures/block/fluid/shimmer_still_red.png"),
                new ResourceLocation(MODID, "textures/block/fluid/shimmer_still_blue.png")
            );
        }
        if (confluence$renderTypes == null) {
            confluence$renderTypes = ImmutableList.<RenderType>builder()
                .addAll(cir.getReturnValue())
                .add(ModRenderTypes.shimmerStillDynamic)
                .build();
        }
        cir.setReturnValue(confluence$renderTypes);
    }
}
