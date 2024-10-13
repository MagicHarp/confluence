package org.confluence.mod.mixin.client.accessor;

import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(PostChain.class)
public interface PostChainAccessor {
    @Invoker
    RenderTarget callGetRenderTarget(@Nullable String pTarget);

    @Accessor
    List<PostPass> getPasses();
}
