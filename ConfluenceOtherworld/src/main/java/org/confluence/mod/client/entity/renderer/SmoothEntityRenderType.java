package org.confluence.mod.client.entity.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;

import java.io.IOException;
import java.util.function.Function;

/// 保留实体受伤叠层和面法线，只将光照贴图的整数级读取改为连续插值。
@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class SmoothEntityRenderType extends RenderType {
    private static ShaderInstance shader;
    private static final Function<ResourceLocation, RenderType> CUTOUT = Util.memoize(texture -> create("confluence_smooth_entity", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, false,
            CompositeState.builder().setShaderState(new ShaderStateShard(() -> shader))
                    .setTextureState(new TextureStateShard(texture, false, false)).setCullState(NO_CULL)
                    .setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).createCompositeState(true)));

    private SmoothEntityRenderType(String name, VertexFormat format, VertexFormat.Mode mode, int size, boolean crumbling, boolean sorting, Runnable setup, Runnable clear) {
        super(name, format, mode, size, crumbling, sorting, setup, clear);
    }

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(), Confluence.asResource("smooth_entity"), DefaultVertexFormat.NEW_ENTITY), instance -> shader = instance);
    }

    public static RenderType cutout(ResourceLocation texture) {
        return shader == null ? RenderType.entityCutoutNoCull(texture) : CUTOUT.apply(texture);
    }
}
