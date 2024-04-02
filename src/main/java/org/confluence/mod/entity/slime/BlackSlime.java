package org.confluence.mod.entity.slime;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlackSlime extends Slime {
    public BlackSlime(EntityType<BlackSlime> slime, Level level) {
        super(slime, level);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor accessor, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag nbt) {
        SpawnGroupData spawnGroupData = super.finalizeSpawn(accessor, difficulty, spawnType, groupData, nbt);
        int size = 2;
        if (accessor.getRandom().nextFloat() < 0.5F * difficulty.getSpecialMultiplier()) size = 4;
        setSize(size, true);
        return spawnGroupData;
    }

    @Override
    public void remove(@NotNull RemovalReason removalReason) {
        if (getSize() == 2) {
            brain.clearMemories();
            setRemoved(removalReason);
            invalidateCaps();
        } else {
            super.remove(removalReason);
        }
    }
}
