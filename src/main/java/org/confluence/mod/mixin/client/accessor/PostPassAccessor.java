package org.confluence.mod.mixin.client.accessor;

import net.minecraft.client.renderer.PostPass;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.function.IntSupplier;

@Mixin(PostPass.class)
public interface PostPassAccessor {
    @Accessor
    List<IntSupplier> getAuxAssets();

    @Accessor
    List<String> getAuxNames();

    @Accessor
    List<Integer> getAuxWidths();

    @Accessor
    List<Integer> getAuxHeights();

    @Accessor
    Matrix4f getShaderOrthoMatrix();
}
