package org.confluence.mod.mixin.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.ServerLevelAccessor;
import org.confluence.mod.entity.npc.ModVillagers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Villager.class)
public abstract class VillagerMixin {
    @Inject(method = "finalizeSpawn", at = @At("RETURN"))
    private void setVillagerType(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, SpawnGroupData pSpawnData, CompoundTag pDataTag, CallbackInfoReturnable<SpawnGroupData> cir) {
        ModVillagers.setVillagerType((Villager) (Object) this);
    }
}
