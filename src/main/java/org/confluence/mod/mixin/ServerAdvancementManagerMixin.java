package org.confluence.mod.mixin;

import com.google.gson.JsonElement;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec2;
import org.confluence.mod.advancement.ModAchievements;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ServerAdvancementManager.class)
public abstract class ServerAdvancementManagerMixin {
    @Shadow
    private AdvancementList advancements;

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At("TAIL"))
    private void confluence$setLocation(Map<ResourceLocation, JsonElement> p_136034_, ResourceManager p_136035_, ProfilerFiller p_136036_, CallbackInfo ci) {
        ModAchievements.initialize();
        for (Map.Entry<ResourceLocation, Vec2> entry : ModAchievements.DISPLAY_OFFSET.entrySet()) {
            Advancement advancement = advancements.get(entry.getKey());
            if (advancement == null) continue;
            Vec2 vec2 = entry.getValue();
            DisplayInfo displayInfo = advancement.getDisplay();
            if (displayInfo == null) return;
            displayInfo.setLocation(vec2.x, vec2.y);
        }
    }
}
