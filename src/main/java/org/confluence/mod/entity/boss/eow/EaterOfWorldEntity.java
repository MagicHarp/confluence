package org.confluence.mod.entity.boss.eow;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.entity.worm.AbstractWormEntity;
import org.confluence.mod.entity.worm.BaseWormPart;
import org.confluence.mod.entity.worm.WormMovementUtils;
import org.confluence.mod.util.ModUtils;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class EaterOfWorldEntity extends AbstractWormEntity {
    public static final int WORM_LENGTH = 72;
    public static final float WORM_HEALTH = 5f;
    // 在这范围内的玩家会被优先考虑作为目标
    public static final float TARGET_RADIUS_NEAR = 80f;
    // 在这范围外的玩家
    public static final float TARGET_RADIUS_FAR = 150f;
    // 多久没有目标后脱战？
    public static final int TIMEOUT_THRESHOLD = 20;
    public static final WormMovementUtils.WormSegmentMovementOptions FOLLOW_INFO =
            new WormMovementUtils.WormSegmentMovementOptions()
                    .setFollowDistance(0.5)
                    .setStraighteningMultiplier(-0.1)
                    .setVelocityOrTeleport(true);

    // 一段时间内没有目标后脱战
    int ticksWithoutTarget = 0;

    public EaterOfWorldEntity(EntityType<? extends AbstractWormEntity> entityType, Level level) {
        super(entityType, level, WORM_LENGTH, WORM_HEALTH);
    }
    @Override
    protected BaseWormPart<? extends AbstractWormEntity> partConstructor(int index) {
        EaterOfWorldPart result = new EaterOfWorldPart(ModEntities.EATER_OF_WORLD_PART.get(), level());
        result.setInfo(this, index, WORM_HEALTH);
        return result;
    }
    @Override
    protected WormMovementUtils.WormSegmentMovementOptions getWormFollowOption() {
        return FOLLOW_INFO;
    }
    @Override
    protected void deathCallback() {
        ModUtils.testMessage(level(), "老爷爷，我来给你call call back");

    }

    // 不要因为距离消失，脱战逻辑自己写
    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH)
                .add(Attributes.ATTACK_DAMAGE)
                .add(Attributes.ARMOR)
                .add(Attributes.MOVEMENT_SPEED);
    }

    /**
     * 将目标玩家更新为maxRadius中最近的一个
     * @param maxRadius 最大索敌半径
     * */
    private void updateTarget(double maxRadius) {
        Player closestCandidate = null;
        double closestDistSqr = 999999d;
        for (Player candidate : level().getEntitiesOfClass(Player.class, getBoundingBox().inflate(maxRadius))) {
            double distSqr = position().distanceToSqr(candidate.position());
            if (distSqr < closestDistSqr) {
                closestCandidate = candidate;
                closestDistSqr = distSqr;
            }
        }
        target = closestCandidate;
    }
    @Override
    public void tick() {
        // 更新目标
        // 目标较远后清空目标以便重新选取
        if (target != null && position().distanceToSqr(target.position()) > TARGET_RADIUS_NEAR * TARGET_RADIUS_NEAR) {
            target = null;
        }
        // 重选目标
        if (target == null) {
            updateTarget(TARGET_RADIUS_NEAR);
        }
        if (target == null) {
            updateTarget(TARGET_RADIUS_FAR);
        }
        // 重选目标失败，若长时间重选失败则直接脱战消失
        if (target == null) {
            if (++ticksWithoutTarget >= TIMEOUT_THRESHOLD) {
                discard();
            }
            return;
        }
        // 重置脱战计数器
        ticksWithoutTarget = 0;

        super.tick();
    }

    // GeoEntity

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, state -> state.setAndContinue(RawAnimation.begin().thenLoop("fly"))));
    }

    private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return CACHE;
    }
}
