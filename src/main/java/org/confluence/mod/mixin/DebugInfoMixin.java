package org.confluence.mod.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(NoiseBasedChunkGenerator.class)
public class DebugInfoMixin {
    @Inject(method = "addDebugScreenInfo", at = @At("RETURN"))
    private void addInfo(List<String> pInfo, RandomState pRandom, BlockPos pPos, CallbackInfo ci){
        String info = pInfo.get(pInfo.size() - 1);
        pInfo.set(pInfo.size() - 1,
            info.replace("T:", "温度:").replace("V:", "湿度:").replace("C:", "大陆性:")
                .replace("E:", "侵蚀度:").replace("D:", "深度:").replace("W:", "奇异性:")
                .replace("PV:", "山脊性:").replace("AS:", "初始密度:").replace("N:", "最终密度:"));
    }
}
