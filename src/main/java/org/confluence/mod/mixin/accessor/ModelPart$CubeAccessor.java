package org.confluence.mod.mixin.accessor;

import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ModelPart.Cube.class)
public interface ModelPart$CubeAccessor {
    @Accessor
    ModelPart.Polygon[] getPolygons();
}
