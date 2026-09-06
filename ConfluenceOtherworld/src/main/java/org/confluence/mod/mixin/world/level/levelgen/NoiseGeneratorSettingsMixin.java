package org.confluence.mod.mixin.world.level.levelgen;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.confluence.mod.common.worldgen.biome.SurfaceRuleData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/// todo 1.3.0
@Mixin(NoiseGeneratorSettings.class)
public abstract class NoiseGeneratorSettingsMixin {
    @Shadow
    public abstract BlockState defaultBlock();

    @Inject(method = "surfaceRule", at = @At("RETURN"), cancellable = true)
    private void confluence$appendEndSurfaceRules(CallbackInfoReturnable<SurfaceRules.RuleSource> callback) {
        if (defaultBlock().is(Blocks.END_STONE)) {
            callback.setReturnValue(SurfaceRules.sequence(SurfaceRuleData.makeConfluenceEndRules(), callback.getReturnValue()));
        }
    }
}
