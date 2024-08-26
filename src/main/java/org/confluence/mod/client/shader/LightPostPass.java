package org.confluence.mod.client.shader;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.server.packs.resources.ResourceManager;
import org.confluence.mod.mixin.client.accessor.PostPassAccessor;

import java.io.IOException;
import java.util.List;
import java.util.function.IntSupplier;

public class LightPostPass extends PostPass {
    public LightPostPass(ResourceManager pResourceManager, String pName, RenderTarget pInTarget, RenderTarget pOutTarget) throws IOException {
        super(pResourceManager, pName, pInTarget, pOutTarget);
    }

    @Override
    public void process(float pPartialTicks) {
        inTarget.unbindWrite();
        float f = (float) outTarget.width;
        float f1 = (float) outTarget.height;
        RenderSystem.viewport(0, 0, (int) f, (int) f1);
        // Custom Start
        EffectInstance effect = getEffect();
        PostPassAccessor accessor = (PostPassAccessor) this;
        List<IntSupplier> auxAssets = accessor.getAuxAssets();
        effect.setSampler("Sampler2", () -> RenderSystem.getShaderTexture(2));
        // Custom End
        effect.setSampler("DiffuseSampler", inTarget::getColorTextureId);

        for (int i = 0; i < auxAssets.size(); ++i) {
            effect.setSampler(accessor.getAuxNames().get(i), auxAssets.get(i));
            effect.safeGetUniform("AuxSize" + i).set((float) accessor.getAuxWidths().get(i), (float) accessor.getAuxHeights().get(i));
        }

        effect.safeGetUniform("ProjMat").set(accessor.getShaderOrthoMatrix());
        effect.safeGetUniform("InSize").set((float) inTarget.width, (float) inTarget.height);
        effect.safeGetUniform("OutSize").set(f, f1);
        effect.safeGetUniform("Time").set(pPartialTicks);
        Minecraft minecraft = Minecraft.getInstance();
        effect.safeGetUniform("ScreenSize").set((float) minecraft.getWindow().getWidth(), (float) minecraft.getWindow().getHeight());
        effect.apply();
        outTarget.clear(Minecraft.ON_OSX);
        outTarget.bindWrite(false);
        RenderSystem.depthFunc(519);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
        bufferbuilder.vertex(0.0D, 0.0D, 500.0D).endVertex();
        bufferbuilder.vertex(f, 0.0D, 500.0D).endVertex();
        bufferbuilder.vertex(f, f1, 500.0D).endVertex();
        bufferbuilder.vertex(0.0D, f1, 500.0D).endVertex();
        BufferUploader.draw(bufferbuilder.end());
        RenderSystem.depthFunc(515);
        effect.clear();
        outTarget.unbindWrite();
        inTarget.unbindRead();

        for (Object object : auxAssets) {
            if (object instanceof RenderTarget) {
                ((RenderTarget) object).unbindRead();
            }
        }
    }
}
