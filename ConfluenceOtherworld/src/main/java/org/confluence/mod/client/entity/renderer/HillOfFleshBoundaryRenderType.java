package org.confluence.mod.client.entity.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class HillOfFleshBoundaryRenderType extends RenderStateShard {
    private static ShaderInstance shader;
    public static final RenderType FIRE = RenderType.create("confluence_hill_boundary", DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS, 8192, false, true,
            RenderType.CompositeState.builder().setShaderState(new ShaderStateShard(HillOfFleshBoundaryRenderType::getShader))
                    .setTextureState(new TextureStateShard(Confluence.asResource("textures/gui/noise.png"), false, false))
                    .setTransparencyState(LIGHTNING_TRANSPARENCY).setCullState(NO_CULL).setWriteMaskState(COLOR_WRITE).createCompositeState(false));

    private HillOfFleshBoundaryRenderType() {
        super("confluence_hill_boundary", () -> {}, () -> {});
    }

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(), Confluence.asResource("hill_boundary"), DefaultVertexFormat.POSITION_TEX_COLOR), instance -> shader = instance);
    }

    private static ShaderInstance getShader() {
        if (shader == null) return GameRenderer.getPositionTexColorShader();
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null && shader.getUniform("Time") != null) {
            shader.getUniform("Time").set(-((minecraft.level.getGameTime() % 100000L) + minecraft.getFrameTime()) * 0.01F);
        }
        return shader;
    }
}
