package org.confluence.mod.client.shader;

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
import org.confluence.mod.client.post.DIYShaderInstance;

import java.io.IOException;

import static org.confluence.mod.Confluence.MODID;

public final class ModRenderTypes extends RenderStateShard {
    public static RenderType shimmerLiquid;






    @Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Shaders {
        private static ShaderInstance entityDynamic;
        private static ShaderInstance shimmerLiquid;


        public static DIYShaderInstance cthSampler;//克眼采样着色器
        public static DIYShaderInstance positionColorSampler;//点颜色采样着色器

        public static DIYShaderInstance motion_blur;
        public static DIYShaderInstance gaussian_blur;
        public static DIYShaderInstance diy_blit;//直接输出到屏幕
        public static DIYShaderInstance diy_blit_gamma;//gamma校正
        public static DIYShaderInstance diy_blit_mix_add;//线性混合相加


        public static ShaderInstance aether;

        @SubscribeEvent
        public static void onRegisterShaders(RegisterShadersEvent event) throws IOException {
            ResourceProvider resourceProvider = event.getResourceProvider();
            event.registerShader(
                    new ShaderInstance(
                            resourceProvider,
                            Confluence.asResource("rendertype_entity_dynamic"),
                            DefaultVertexFormat.NEW_ENTITY),
                    shader -> entityDynamic = shader
            );

            event.registerShader(
                    new ShaderInstance(
                            resourceProvider,
                            Confluence.asResource("shimmer_liquid"),
                            DefaultVertexFormat.BLOCK),
                    shader -> shimmerLiquid = shader
            );
//            event.registerShader(
//                new ShaderInstance(
//                    resourceProvider,
//                    Confluence.asResource("color"),
//                    DefaultVertexFormat.POSITION_TEX_LIGHTMAP_COLOR),
//                shader -> aether = shader
//            );


            event.registerShader(
                    new DIYShaderInstance(
                            resourceProvider,
                            Confluence.asResource("cth"),
                            DefaultVertexFormat.NEW_ENTITY,
                            um->{}//um.createUniform("samcolor")
                            ),
                    shader -> cthSampler = (DIYShaderInstance) shader
            );
            event.registerShader(
                    new DIYShaderInstance(
                            resourceProvider,
                            Confluence.asResource("position_color"),
                            DefaultVertexFormat.POSITION_COLOR,
                            um->um.createUniform("colorMask")
                    ),
                    shader -> positionColorSampler = (DIYShaderInstance) shader
            );


            event.registerShader(
                    new DIYShaderInstance(
                            resourceProvider,
                            Confluence.asResource("diy_blit"),
                            DefaultVertexFormat.BLIT_SCREEN,
                            um->um.createUniform("colorMask")
                            ),
                    shader -> diy_blit = (DIYShaderInstance) shader
            );

            event.registerShader(
                    new DIYShaderInstance(
                            resourceProvider,
                            Confluence.asResource("diy_blit_mix_add"),
                            DefaultVertexFormat.BLIT_SCREEN,
                            null
                    ),
                    shader -> diy_blit_mix_add = (DIYShaderInstance) shader
            );
            event.registerShader(
                    new DIYShaderInstance(
                            resourceProvider,
                            Confluence.asResource("diy_blit_gamma"),
                            DefaultVertexFormat.BLIT_SCREEN,
                            null
                    ),
                    shader -> diy_blit_gamma = (DIYShaderInstance) shader
            );
            event.registerShader(
                    new DIYShaderInstance(
                            resourceProvider,
                            Confluence.asResource("motion_blur"),
                            DefaultVertexFormat.BLIT_SCREEN,
                            um->{
                                um.createUniform("dist");
                                um.createUniform("dir");
                            }
                    ),
                    shader -> motion_blur = (DIYShaderInstance) shader
            );
            event.registerShader(
                    new DIYShaderInstance(
                            resourceProvider,
                            Confluence.asResource("gaussian_blur"),
                            DefaultVertexFormat.BLIT_SCREEN,
                            um->{
                                um.createUniform("hor");
                            }
                    ),
                    shader -> gaussian_blur = (DIYShaderInstance) shader
            );

        }
    }



    public static final ShaderStateShard ENTITY_DYNAMIC_SHADER = new ShaderStateShard(() -> Shaders.entityDynamic);
    public static final ShaderStateShard SHIMMER_LIQUID_SHADER = new ShaderStateShard(() -> Shaders.shimmerLiquid);
    public static final ShaderStateShard CTH_SHADER = new ShaderStateShard(() -> Shaders.cthSampler);
    public static final ShaderStateShard DIY_BLIT = new ShaderStateShard(() -> Shaders.diy_blit);


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

    public static RenderType getShimmerLiquid() {
        return RenderType.create(
            MODID + ":shimmer_liquid",
            DefaultVertexFormat.BLOCK,
            VertexFormat.Mode.QUADS,
            2097152, true, true,
            RenderType.CompositeState.builder()
                .setLightmapState(LIGHTMAP)
                .setShaderState(SHIMMER_LIQUID_SHADER)
                .setTextureState(BLOCK_SHEET_MIPPED)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setOutputState(TRANSLUCENT_TARGET)
                .createCompositeState(true)
        );
    }

    public static RenderType cthRenderType(ResourceLocation tex) {
        return RenderType.create(MODID + "cth_render_type",
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS,
                256,
                true,
                false,
                RenderType.CompositeState.builder()
                        .setShaderState(CTH_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(tex, false, false))
                        .setTransparencyState(NO_TRANSPARENCY)
                        .setCullState(NO_CULL)
                        .setLightmapState(LIGHTMAP)
                        .setOverlayState(OVERLAY)
                        //.setOutputState(RenderStateShard.OUTLINE_TARGET)
                        .createCompositeState(false)
        );

    }



    private ModRenderTypes() {
        super(null, null, null);
    }

}
