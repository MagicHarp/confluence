package org.confluence.mod.mixin.client;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.RenderType;
import org.confluence.mod.client.ModRenderTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = RenderType.class, priority = 10000)
public class RenderTypeMixin {
    @Unique
    private static List<RenderType> confluence$renderTypes;

    @Inject(method = "chunkBufferLayers", at = @At("RETURN"), cancellable = true)
    private static void put(CallbackInfoReturnable<List<RenderType>> cir) {
        if (ModRenderTypes.shimmerStillDynamic == null) {
            ModRenderTypes.shimmerStillDynamic = ModRenderTypes.getBlockDynamic();
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
