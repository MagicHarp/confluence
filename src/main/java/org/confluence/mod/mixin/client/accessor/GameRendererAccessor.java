package org.confluence.mod.mixin.client.accessor;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameRenderer.class)
public interface GameRendererAccessor {
    @Accessor
    PostChain getPostEffect();

    @Accessor
    void setPostEffect(PostChain postEffect);

    @Accessor
    ResourceManager getResourceManager();

    @Accessor
    void setEffectActive(boolean effectActive);

    @Accessor
    void setEffectIndex(int effectIndex);
}
