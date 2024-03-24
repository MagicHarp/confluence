package org.confluence.mod.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.Tags;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.ModClient;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BiomeColors.class)
public abstract class BiomeColorsMixin {
    @Shadow
    @Final
    public static ColorResolver WATER_COLOR_RESOLVER;
    @Unique
    private static final TagKey<Biome> confluence$is_hallow = TagKey.create(Registries.BIOME, new ResourceLocation(Confluence.MODID, "is_hallow"));

    @Inject(method = "getAverageColor", at = @At(value = "RETURN"), cancellable = true)
    private static void changeColor(BlockAndTintGetter getter, BlockPos pos, ColorResolver resolver, CallbackInfoReturnable<Integer> cir) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null || resolver != WATER_COLOR_RESOLVER) return;
        if (ClientPacketHandler.showHolyWaterColor() && level.getBiome(pos).is(Tags.Biomes.IS_DESERT)) {
            cir.setReturnValue(getter.getBlockTint(pos, ModClient.HALLOW_WATER_RESOLVER));
        }
    }
}
