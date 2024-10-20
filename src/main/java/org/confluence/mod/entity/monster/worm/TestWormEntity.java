package org.confluence.mod.entity.monster.worm;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.util.ModUtils;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;

public class TestWormEntity extends AbstractWormEntity {
    public static final int WORM_LENGTH = 5;
    public static final float WORM_HEALTH = 1f;
    public static final WormMovementUtils.WormSegmentMovementOptions FOLLOW_INFO =
            new WormMovementUtils.WormSegmentMovementOptions()
                    .setFollowDistance(0.5)
                    .setStraighteningMultiplier(-0.1)
                    .setVelocityOrTeleport(true);

    public TestWormEntity(EntityType<? extends AbstractWormEntity> entityType, Level level) {
        super(entityType, level, WORM_LENGTH, WORM_HEALTH);
    }
    @Override
    protected BaseWormPart<? extends AbstractWormEntity> partConstructor(int index) {
//        return new TestWormPart(this, index, WORM_HEALTH);
        TestWormPart result = new TestWormPart(ModEntities.TEST_WORM_PART.get(), level());
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




    @Override
    public boolean isPushable(){
        return false;
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH)
                .add(Attributes.ATTACK_DAMAGE)
                .add(Attributes.ARMOR)
                .add(Attributes.MOVEMENT_SPEED);
    }


    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return new ArrayList<>();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot pSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {

    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
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
