package org.confluence.mod.mixin.client;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.RenderType;
import org.confluence.mod.client.shader.ModRenderTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = RenderType.class, priority = 10000)
public class RenderTypeMixin { // 移除abstract是标记为待定,所以没有加入mixin.json
    @Unique
    private static List<RenderType> confluence$renderTypes;

    @Inject(method = "chunkBufferLayers", at = @At("RETURN"), cancellable = true)
    private static void put(CallbackInfoReturnable<List<RenderType>> cir) {
        if (ModRenderTypes.shimmerLiquid == null) {
            ModRenderTypes.shimmerLiquid = ModRenderTypes.getShimmerLiquid();
        }
        if (confluence$renderTypes == null) {
            confluence$renderTypes = ImmutableList.<RenderType>builder()
                .addAll(cir.getReturnValue())
                .add(ModRenderTypes.shimmerLiquid)
                .build();
        }
        cir.setReturnValue(confluence$renderTypes);
    }
}
