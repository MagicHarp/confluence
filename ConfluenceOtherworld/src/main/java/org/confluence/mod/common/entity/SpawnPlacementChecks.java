package org.confluence.mod.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.confluence.mod.common.CommonConfigs;
import org.confluence.mod.mixed.ILevelChunkSection;
import org.confluence.mod.mixed.IMinecraftServer;
import org.confluence.mod.util.DynamicBiomeUtils;
import org.confluence.mod.util.OverworldUtils;

/// 自然生成使用的公共环境校验集合。
///
/// 生物群系修饰器只负责把实体类型放入某个生物群系的候选表，真正生成前仍会经过这里注册的
/// 放置规则。因此高度、维度、昼夜、天气、视野和困难模式等硬约束必须集中在此处，不能只依赖
/// JSON 中的权重或生物群系选择。
///
/// 各方法负责环境与支撑条件；实体遮挡检查仍由后续生成流程执行。
/// 水生敌怪使用水域条件，不能复用要求脚下支撑方块的陆生 Mob 规则。
public final class SpawnPlacementChecks {
    private SpawnPlacementChecks() {}

    public static boolean checkRoutineMonsterSpawn(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return pos.getY() < OverworldUtils.getSpaceY() && checkMonsterSpawnRules(type, level, spawnType, pos, random);
    }

    public static boolean checkGroundSpawn(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        int y = pos.getY();
        return y >= OverworldUtils.getSurfaceY() && y < OverworldUtils.getSpaceY() && checkMonsterSpawnRules(type, level, spawnType, pos, random);
    }

    public static boolean checkUndergroundMonsterSpawn(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        int y = pos.getY();
        return y >= OverworldUtils.getUndergroundY() && y < OverworldUtils.getSurfaceY() && !level.canSeeSky(pos) && checkMonsterSpawnRules(type, level, spawnType, pos, random);
    }

    public static boolean checkCaveMonsterSpawn(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return pos.getY() < OverworldUtils.getUndergroundY() && !level.canSeeSky(pos) && checkMonsterSpawnRules(type, level, spawnType, pos, random);
    }

    /// 地下层与洞穴层共用的放置规则，适用于泰拉中标注为“地下及更深处”的敌怪。
    public static boolean checkBelowSurfaceMonsterSpawn(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return pos.getY() < OverworldUtils.getSurfaceY() && !level.canSeeSky(pos) && checkMonsterSpawnRules(type, level, spawnType, pos, random);
    }

    public static boolean checkDungeonMonsterSpawn(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return pos.getY() >= -35 && pos.getY() <= 40 && !level.canSeeSky(pos) && checkMonsterSpawnRules(type, level, spawnType, pos, random);
    }

    public static boolean checkHighLevelMonsterSpawn(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return pos.getY() >= OverworldUtils.getSpaceY() && pos.getY() < level.getMaxBuildHeight() && checkMonsterSpawnRules(type, level, spawnType, pos, random);
    }

    public static boolean checkNetherMonsterSpawn(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level instanceof Level world && world.dimension() == OverworldUtils.underworld() && checkMonsterSpawnRules(type, level, spawnType, pos, random);
    }

    public static boolean checkFlyingFishSpawn(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level instanceof Level world && world.isRaining() && checkSurfaceMobSpawn(type, level, spawnType, pos, random);
    }

    public static boolean checkDemonEyeSpawn(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (!(level instanceof Level world) || !checkGroundSpawn(type, level, spawnType, pos, random) || !world.isNight())
            return false;
        if (world.getMoonPhase() == 4) return hasClearColumn(world, pos);
        return world.random.nextInt(99) < 80;
    }

    public static boolean checkWraithSpawn(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (!(level instanceof Level world) || !world.isNight() || !checkGroundSpawn(type, level, spawnType, pos, random))
            return false;
        return world.getMoonPhase() == 4 || random.nextInt(4) == 0;
    }

    public static boolean checkGhostSpawn(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        ILevelChunkSection section = DynamicBiomeUtils.getISection(level, pos);
        return section != null && section.confluence$isGraveyard() && checkRoutineMonsterSpawn(type, level, spawnType, pos, random);
    }

    public static boolean checkPossessedArmorSpawn(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (!checkMonsterSpawnRules(type, level, spawnType, pos, random)) {
            return false;
        }
        int y = pos.getY();
        boolean validAltitude = y >= level.getMinBuildHeight() && (y < OverworldUtils.getSurfaceY() || y < OverworldUtils.getSpaceY() && level instanceof Level world && world.isNight());
        return validAltitude && level instanceof Level world && hasClearColumn(world, pos);
    }

    public static boolean checkWaterMonsterSpawn(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return pos.getY() < OverworldUtils.getSpaceY() && hasDeepWater(level, pos);
    }

    public static boolean checkSurfaceWaterMonsterSpawn(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        int y = pos.getY();
        return y >= OverworldUtils.getSurfaceY() && y < OverworldUtils.getSpaceY() && hasDeepWater(level, pos);
    }

    public static boolean checkUndergroundWaterMonsterSpawn(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return pos.getY() < OverworldUtils.getSurfaceY() && !level.canSeeSky(pos) && hasDeepWater(level, pos);
    }

    public static boolean checkGoblinScoutSpawn(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return checkSurfaceDayMobSpawn(type, level, spawnType, pos, random);
    }

    /// 白天地表敌怪不能套用原版怪物亮度门槛，否则会在满足时间条件时反而无法自然生成。
    public static boolean checkSurfaceDayMobSpawn(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level instanceof Level world && world.isDay() && checkSurfaceMobSpawn(type, level, spawnType, pos, random);
    }

    public static boolean checkSurfaceMobSpawn(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        int y = pos.getY();
        return y >= OverworldUtils.getSurfaceY() && y < OverworldUtils.getSpaceY() && Mob.checkMobSpawnRules(type, level, spawnType, pos, random);
    }

    public static boolean checkRoutineMobSpawn(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return pos.getY() < OverworldUtils.getSpaceY() && Mob.checkMobSpawnRules(type, level, spawnType, pos, random);
    }

    public static boolean checkSurfaceNightMonsterSpawn(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level instanceof Level world && world.isNight() && checkGroundSpawn(type, level, spawnType, pos, random);
    }

    public static <T extends Entity> SpawnPlacements.SpawnPredicate<T> hardmode(SpawnPlacements.SpawnPredicate<T> predicate) {
        // 包装既有规则而不是复制一份，确保开启困难模式只增加进度门槛，不改变环境语义。
        return (type, level, spawnType, pos, random) -> level instanceof ServerLevel serverLevel
                && IMinecraftServer.isHardmode(serverLevel.getServer())
                && predicate.test(type, level, spawnType, pos, random);
    }

    /// 执行所有敌对生物共用的原版基础放置检查，并按配置决定是否保留亮度门槛。
    ///
    /// 具有额外昼夜、地形或进度条件的实体也必须在自身条件之后调用本方法，不能直接调用
    /// {@link Monster#checkMonsterSpawnRules}，否则它们会绕过统一的亮度配置。
    @SuppressWarnings("unchecked")
    public static boolean checkMonsterSpawnRules(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        // 忽略光照时仍保留 Mob 对脚下方块的生成支撑检查；碰撞和边界由外部生成流程处理。
        if (CommonConfigs.SPAWN_WITHOUT_LIGHT.get()) {
            return Mob.checkMobSpawnRules(type, level, spawnType, pos, random);
        }
        EntityType<? extends Monster> monsterType = (EntityType<? extends Monster>) type;
        return Monster.checkMonsterSpawnRules(monsterType, level, spawnType, pos, random);
    }

    private static boolean hasClearColumn(Level level, BlockPos pos) {
        // 附身盔甲需要无遮挡的纵向空间；遇到第一个完整碰撞方块即可提前失败。
        BlockPos.MutableBlockPos cursor = pos.mutable();
        while (cursor.getY() < level.getMaxBuildHeight()) {
            if (level.getBlockState(cursor).isCollisionShapeFullBlock(level, cursor)) return false;
            cursor.move(0, 1, 0);
        }
        return true;
    }

    private static boolean hasDeepWater(ServerLevelAccessor level, BlockPos pos) {
        return level.getFluidState(pos).is(FluidTags.WATER)
                && level.getFluidState(pos.above()).is(FluidTags.WATER);
    }
}
