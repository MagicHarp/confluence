package org.confluence.mod.entity.worm;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.confluence.mod.util.ModUtils;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TestWormPart extends BaseWormPart<TestWormEntity> implements GeoEntity {
    int indexAI = 0;

    /** 注册用的constructor；实际使用下方的另一个。体节应该被主实体集中创建 */
    public TestWormPart(EntityType<? extends TestWormPart> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
//    /** 实际使用的constructor */
//    public TestWormPart(TestWormEntity parent, int segmentIndex, float MAX_HEALTH) {
//        super(parent, segmentIndex, MAX_HEALTH);
//    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH)
                .add(Attributes.ATTACK_DAMAGE)
                .add(Attributes.ARMOR)
                .add(Attributes.MOVEMENT_SPEED);
    }


    @Override
    protected void tickSegment() {
        super.tickSegment();

        if (this.segmentType == SegmentType.HEAD) {
            setDeltaMovement(Mth.sin(indexAI * Mth.TWO_PI / 100f) * 0.5,
                    Mth.sin(indexAI * Mth.TWO_PI / 60f) * 0.25,
                    Mth.cos(indexAI * Mth.TWO_PI / 100f) * 0.5);
            ModUtils.updateEntityRotation(this, getDeltaMovement());
        }

        indexAI ++;
    }

    @Override
    public void tick() {
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
