package org.confluence.mod.entity.worm.test;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.worm.BaseWormPart;
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
        super(null, 0, 0f);
        ModUtils.testMessage(level(), "wrong constructor...");
    }
    /** 实际使用的constructor */
    public TestWormPart(TestWormEntity parent, int segmentIndex, float maxHealth) {
        super(parent, segmentIndex, maxHealth);
        ModUtils.testMessage(level(), segmentIndex + "I2");
    }



    @Override
    protected void tickSegment() {
        super.tickSegment();
        ModUtils.testMessage(level(), segmentIndex + " : " + position());
        ModUtils.testMessage(level(), segmentType + ", " + getDeltaMovement());

        if (this.segmentType == SegmentType.HEAD) {
            setDeltaMovement(0.5, Mth.sin(++indexAI * Mth.TWO_PI / 60f), 0);
        }
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
