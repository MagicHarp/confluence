package org.confluence.mod.mixin.client.resources.model;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.terra_furniture.TerraFurniture;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Set;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {
    @Unique
    private static final Set<String> confluence$skipSet = Set.of(Confluence.MODID, TerraFurniture.MODID);

    /// "Unable to load model: '{}' referenced from: {}: {}"
    @WrapWithCondition(method = "getModel", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;[Ljava/lang/Object;)V", remap = false))
    private boolean skipConfluenceLog(Logger instance, String s, Object[] objects) {
        return objects.length == 0 || !(objects[0] instanceof ResourceLocation rl) || !confluence$skipSet.contains(rl.getNamespace());
    }

    // "Exception loading blockstate definition: '{}' missing model for variant: '{}'"
    @WrapWithCondition(method = "lambda$loadModel$22", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V", ordinal = 0, remap = false), remap = false)
    private boolean skipConfluenceLog(Logger instance, String s, Object o0, Object o1) {
        return !(o0 instanceof ResourceLocation rl) || !confluence$skipSet.contains(rl.getNamespace());
    }
}
