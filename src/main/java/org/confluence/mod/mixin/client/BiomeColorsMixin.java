package org.confluence.mod.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import org.confluence.mod.client.ModClient;
import org.confluence.mod.client.color.IntegerRGB;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.confluence.mod.worldgen.biome.ModBiomes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BiomeColors.class)
public abstract class BiomeColorsMixin {
    @Shadow
    @Final
    public static ColorResolver WATER_COLOR_RESOLVER;

    @Inject(method = "getAverageColor", at = @At(value = "RETURN"), cancellable = true)
    private static void changeColor(BlockAndTintGetter getter, BlockPos pos, ColorResolver resolver, CallbackInfoReturnable<Integer> cir) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        if (ClientPacketHandler.isBloodyMoon()) {
            cir.setReturnValue(IntegerRGB.RED.mixture(cir.getReturnValue(), 0.1F));
        } else if (resolver == WATER_COLOR_RESOLVER && ClientPacketHandler.showHolyWaterColor() && level.getBiome(pos).is(ModBiomes.THE_HALLOW)) {
            cir.setReturnValue(getter.getBlockTint(pos, ModClient.HALLOW_WATER_RESOLVER));
        }
    }
}
