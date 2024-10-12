package org.confluence.mod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class utils {

    public static boolean checkSpawn(EntityType<? extends Mob> pType, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        return pSpawnType == MobSpawnType.SPAWNER || predicate.test(pLevel.getBlockState(blockpos), pLevel, blockpos, pType);
    }
    static BlockBehaviour.StateArgumentPredicate<EntityType<?>> predicate = (blockstate , blockgetter , blockpos , type) ->
            blockstate.isFaceSturdy(blockgetter, blockpos, Direction.UP) && blockgetter.getLightEmission(blockpos) < 14;;



}
