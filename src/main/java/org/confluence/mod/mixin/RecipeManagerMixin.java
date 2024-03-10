package org.confluence.mod.mixin;

import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {
    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At("HEAD"))
    private void removeGolden(Map<ResourceLocation, JsonElement> map, ResourceManager manager, ProfilerFiller filler, CallbackInfo ci) {
        String[] toRemove = new String[]{"axe", "boots", "chestplate", "helmet", "hoe", "leggings", "pickaxe", "shovel", "sword"};
        for (String type : toRemove) {
            map.remove(new ResourceLocation("golden_" + type));
        }
    }
}
