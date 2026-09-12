package org.confluence.mod.client.effect;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;

import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RenderStateShardAccessor extends RenderStateShard {
    private static ShaderInstance unlitShader;
    public static final Function<ResourceLocation, RenderType> UNLIT_TRANSLUCENT = Util.memoize(texture -> RenderType.create("confluence_unlit_translucent", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true,
            RenderType.CompositeState.builder().setShaderState(new ShaderStateShard(() -> unlitShader == null ? GameRenderer.getRendertypeEyesShader() : unlitShader))
                    .setTextureState(new TextureStateShard(texture, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setCullState(NO_CULL).setLightmapState(NO_LIGHTMAP).setOverlayState(NO_OVERLAY).setWriteMaskState(COLOR_WRITE).createCompositeState(false)));
    public static final RenderType TRAIL_RENDER_TYPE = RenderType.create(
            "trail_render_type",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.QUADS,
            1536,
            false,
            true,
            RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_LIGHTNING_SHADER)
                    .setWriteMaskState(COLOR_DEPTH_WRITE)
                    .setTransparencyState(LIGHTNING_TRANSPARENCY)
                    .setLightmapState(NO_LIGHTMAP)
                    .setOutputState(WEATHER_TARGET)
                    .createCompositeState(false));
    public static final RenderType ENTITY_TRANSLUCENT_EMISSIVE = RenderType.create("entity_translucent_emissive", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, false,
            RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
                    .setTextureState(new TextureStateShard(Confluence.asResource("textures/mask/sword.png"), true, false))
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setWriteMaskState(COLOR_WRITE)
                    .setCullState(NO_CULL)
                    .setOverlayState(OVERLAY)
                    .createCompositeState(false));
    public static final BiFunction<ResourceLocation, TransparencyStateShard, RenderType> EYES = Util.memoize(
            (texture, transparencyStateShard) -> {
                RenderStateShard.TextureStateShard textureStateShard = new RenderStateShard.TextureStateShard(texture, false, false);
                return RenderType.create("confluence_eyes", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true,
                        RenderType.CompositeState.builder()
                                .setShaderState(RENDERTYPE_EYES_SHADER)
                                .setTextureState(textureStateShard)
                                .setTransparencyState(transparencyStateShard)
                                .setWriteMaskState(COLOR_WRITE)
                                .createCompositeState(false));
            });

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(), Confluence.asResource("unlit_translucent"), DefaultVertexFormat.NEW_ENTITY), instance -> unlitShader = instance);
    }

    public static RenderType createTextOutline(ResourceLocation texture) {
        return RenderType.create("confluence_outline_text", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(RENDERTYPE_TEXT_SHADER)
                        .setTextureState(new TextureStateShard(texture, false, false))
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setLightmapState(LIGHTMAP)
                        .createCompositeState(true));
    }

    private RenderStateShardAccessor() {
        super(null, null, null);
    }

    public static ColoredGlintContext create(String name, float red, float green, float blue) {
        float[] glintColor = {red, green, blue};
        ColoredGlintContext context = new ColoredGlintContext(RenderType.create(
                "colored_glint_" + name,
                DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS,
                1536,
                false,
                false,
                RenderType.CompositeState.builder()
                        .setShaderState(RENDERTYPE_GLINT_SHADER)
                        .setTextureState(ColoredGlintContext.COLORED_GLINT_TEXTURE_STATE_SHARD)
                        .setWriteMaskState(COLOR_WRITE)
                        .setCullState(NO_CULL)
                        .setDepthTestState(EQUAL_DEPTH_TEST)
                        .setTransparencyState(GLINT_TRANSPARENCY)
                        .setTexturingState(GLINT_TEXTURING)
                        .setColorLogicState(new ColorLogicStateShard("set_color",
                                () -> RenderSystem.setShaderColor(glintColor[0], glintColor[1], glintColor[2], 1.0F),
                                () -> RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F)))
                        .createCompositeState(false)), glintColor);
        ColoredGlintContext.COLORED_GLINT_CONTEXTS.add(context);
        return context;
    }
}
