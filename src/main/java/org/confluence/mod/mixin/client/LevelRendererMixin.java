package org.confluence.mod.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.ViewArea;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.confluence.mod.mixinauxiliary.ILevelRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin implements ILevelRenderer {
    @Shadow
    public abstract void needsUpdate();

    @Shadow
    @Nullable
    private ViewArea viewArea;
    @Unique
    private static final float confluence$size = 10.0F;
    @Unique
    private static final ResourceLocation NO_MOON = Confluence.asResource("textures/environment/no_moon.png");

    @Inject(method = "renderSky",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getStarBrightness(F)F"),
            locals = LocalCapture.CAPTURE_FAILSOFT)
    private void drawSpecificMoon(PoseStack p_202424_, Matrix4f p_254034_, float p_202426_, Camera p_202427_, boolean p_202428_, Runnable p_202429_, CallbackInfo ci, FogType fogtype, Vec3 vec3, float f, float f1, float f2, BufferBuilder bufferbuilder, ShaderInstance shaderinstance, float[] afloat, float f11, Matrix4f matrix4f1) {
        if (ClientPacketHandler.getMoonTexture() == null) return;
        RenderSystem.setShaderTexture(0, ClientPacketHandler.getMoonTexture());
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f1, -confluence$size, -100.0F, confluence$size).uv(0.0F, 1.0F).endVertex();
        bufferbuilder.vertex(matrix4f1, confluence$size, -100.0F, confluence$size).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex(matrix4f1, confluence$size, -100.0F, -confluence$size).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(matrix4f1, -confluence$size, -100.0F, -confluence$size).uv(0.0F, 0.0F).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
    }

    @ModifyArg(method = "renderSky", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V", ordinal = 1), index = 1)
    private ResourceLocation noMoon(ResourceLocation path) {
        if (ClientPacketHandler.getMoonTexture() == null) return path;
        return NO_MOON;
    }

    @Override
    public void confluence$rebuildAllChunks() {
        if (isRubidiumLoaded) {
            confluence$rebuildAllChunksSodium();
        } else {
            if (Minecraft.getInstance().level != null && Minecraft.getInstance().player != null && viewArea != null) {
                for (ChunkRenderDispatcher.RenderChunk chunk : viewArea.chunks) {
                    chunk.setDirty(true);
                }
                needsUpdate();
            }
        }
    }

    @Unique
    private void confluence$rebuildAllChunksSodium() {
        Level world = Minecraft.getInstance().level;
        if (world != null) {
            LocalPlayer clientPlayerEntity = Minecraft.getInstance().player;
            ChunkPos chunkPos = clientPlayerEntity.chunkPosition();
            int viewDistance = Minecraft.getInstance().options.renderDistance().get();
            int startY = world.getMinSection();
            int endY = world.getMaxSection();

            for (int x = -viewDistance; x < viewDistance; ++x) {
                for (int z = -viewDistance; z < viewDistance; ++z) {
                    LevelChunk chunk = Minecraft.getInstance().level.getChunkSource().getChunk(chunkPos.x + x, chunkPos.z + z, false);
                    if (chunk != null && viewArea != null) {
                        for (int y = startY; y <= endY; ++y) {
                            viewArea.setDirty(chunk.getPos().x, y, chunk.getPos().z, false);
                        }
                    }
                }
            }
        }
    }
}
