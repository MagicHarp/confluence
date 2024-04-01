package org.confluence.mod.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    @Unique
    private static final float c$size = 20.0F;
    @Unique
    private static final ResourceLocation NO_MOON = new ResourceLocation(Confluence.MODID, "textures/environment/no_moon.png");

    @Inject(method = "renderSky",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getStarBrightness(F)F", shift = At.Shift.BEFORE),
        locals = LocalCapture.CAPTURE_FAILSOFT)
    private void drawSpecificMoon(PoseStack p_202424_, Matrix4f p_254034_, float p_202426_, Camera p_202427_, boolean p_202428_, Runnable p_202429_, CallbackInfo ci, FogType fogtype, Vec3 vec3, float f, float f1, float f2, BufferBuilder bufferbuilder, ShaderInstance shaderinstance, float[] afloat, float f11, Matrix4f matrix4f1) {
        if (ClientPacketHandler.getSpecificMoon() == null) return;
        RenderSystem.setShaderTexture(0, ClientPacketHandler.getSpecificMoon());
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f1, -c$size, -100.0F, c$size).uv(0.0F, 1.0F).endVertex();
        bufferbuilder.vertex(matrix4f1, c$size, -100.0F, c$size).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex(matrix4f1, c$size, -100.0F, -c$size).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(matrix4f1, -c$size, -100.0F, -c$size).uv(0.0F, 0.0F).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
    }

    @ModifyArg(method = "renderSky", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V", ordinal = 1), index = 1)
    private ResourceLocation noMoon(ResourceLocation path) {
        if (ClientPacketHandler.getSpecificMoon() == null) return path;
        return NO_MOON;
    }
}
