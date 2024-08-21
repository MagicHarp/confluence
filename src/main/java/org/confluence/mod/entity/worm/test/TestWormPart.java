package org.confluence.mod.entity.worm.test;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.worm.AbstractWormEntity;
import org.confluence.mod.entity.worm.BaseWormPart;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TestWormPart extends BaseWormPart<TestWormEntity> implements GeoEntity {
    int indexAI = 0;

    public TestWormPart(EntityType<Entity> entityEntityType, Level level) {
        super(null, null, 0, 0f);
    }
    public TestWormPart(TestWormEntity parent, int segmentIndex, float maxHealth) {
        super(null, parent, segmentIndex, maxHealth);
    }


    @Override
    protected void tickSegment() {
        super.tickSegment();

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
