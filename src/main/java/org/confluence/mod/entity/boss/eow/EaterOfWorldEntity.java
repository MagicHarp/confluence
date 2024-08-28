package org.confluence.mod.entity.boss.eow;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.entity.worm.AbstractWormEntity;
import org.confluence.mod.entity.worm.BaseWormPart;
import org.confluence.mod.entity.worm.WormMovementUtils;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class EaterOfWorldEntity extends AbstractWormEntity {
    public static final int WORM_LENGTH = 72;
    public static final float WORM_HEALTH = 5f;
    public static final WormMovementUtils.WormSegmentMovementOptions FOLLOW_INFO =
            new WormMovementUtils.WormSegmentMovementOptions()
                    .setFollowDistance(0.5)
                    .setStraighteningMultiplier(-0.1)
                    .setVelocityOrTeleport(true);

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

    }


    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH)
                .add(Attributes.ATTACK_DAMAGE)
                .add(Attributes.ARMOR)
                .add(Attributes.MOVEMENT_SPEED);
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
