package org.confluence.mod.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;

import java.io.IOException;

import static org.confluence.mod.Confluence.MODID;

public final class ModRenderTypes extends RenderStateShard {
    public static RenderType shimmerStillDynamic;

    private ModRenderTypes() {
        super(null, null, null);
    }

    public static final ShaderStateShard ENTITY_DYNAMIC_SHADER = new ShaderStateShard(() -> Shaders.entityDynamic);
    public static final ShaderStateShard BLOCK_DYNAMIC_SHADER = new ShaderStateShard(() -> Shaders.blockDynamic);

    public static RenderType getEntityDynamic(ResourceLocation... textures) {
        MultiTextureStateShard.Builder builder = MultiTextureStateShard.builder();
        for (ResourceLocation texture : textures) {
            builder.add(texture, false, false);
        }
        return RenderType.create(
            MODID + ":entity_dynamic",
            DefaultVertexFormat.NEW_ENTITY,
            VertexFormat.Mode.QUADS,
            256, true, false,
            RenderType.CompositeState.builder()
                .setShaderState(ENTITY_DYNAMIC_SHADER)
                .setTextureState(builder.build())
                .setTransparencyState(NO_TRANSPARENCY)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .createCompositeState(true)
        );
    }

    public static RenderType getBlockDynamic() {
        return RenderType.create(
            MODID + ":block_dynamic",
            DefaultVertexFormat.BLOCK,
            VertexFormat.Mode.QUADS,
            2097152, true, true,
            RenderType.CompositeState.builder()
                .setLightmapState(LIGHTMAP)
                .setShaderState(BLOCK_DYNAMIC_SHADER)
                .setTextureState(BLOCK_SHEET_MIPPED)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setOutputState(TRANSLUCENT_TARGET)
                .createCompositeState(true)
        );
    }

    @Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Shaders {
        private static ShaderInstance entityDynamic;
        private static ShaderInstance blockDynamic;

        @SubscribeEvent
        public static void onRegisterShaders(RegisterShadersEvent event) throws IOException {
            ResourceProvider resourceProvider = event.getResourceProvider();
            event.registerShader(
                new ShaderInstance(
                    resourceProvider,
                    new ResourceLocation(MODID, "rendertype_entity_dynamic"),
                    DefaultVertexFormat.NEW_ENTITY),
                shader -> entityDynamic = shader
            );
            event.registerShader(
                new ShaderInstance(
                    resourceProvider,
                    new ResourceLocation(MODID, "rendertype_block_dynamic"),
                    DefaultVertexFormat.BLOCK),
                shader -> blockDynamic = shader
            );
        }
    }
}
