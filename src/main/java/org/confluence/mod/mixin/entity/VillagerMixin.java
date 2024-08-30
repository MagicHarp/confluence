package org.confluence.mod.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerType;
import org.confluence.mod.entity.npc.ModVillagers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Villager.class)
public abstract class VillagerMixin {
    @ModifyExpressionValue(method = "finalizeSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/npc/VillagerType;byBiome(Lnet/minecraft/core/Holder;)Lnet/minecraft/world/entity/npc/VillagerType;"))
    private VillagerType setVillagerType(VillagerType original) {
        return ModVillagers.setVillagerType((Villager) (Object) this, original);
    }
}
