package org.confluence.mod.entity.worm.test;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.worm.AbstractWormEntity;
import org.confluence.mod.entity.worm.BaseWormPart;
import org.confluence.mod.entity.worm.WormMovementUtils;
import org.confluence.mod.util.ModUtils;

public class TestWormEntity extends AbstractWormEntity {
    public static final int WORM_LENGTH = 40;
    public static final float WORM_HEALTH = 20f;
    public static final WormMovementUtils.WormSegmentMovementOptions FOLLOW_INFO =
            new WormMovementUtils.WormSegmentMovementOptions()
                    .setFollowDistance(1.5)
                    .setStraighteningMultiplier(-0.1)
                    .setVelocityOrTeleport(true);

    @Override
    protected BaseWormPart<? extends AbstractWormEntity> partConstructor(int index) {
        return new TestWormPart(this, index, WORM_HEALTH);
    }
    public TestWormEntity(EntityType<? extends AbstractWormEntity> entityType, Level level) {
        super(entityType, level, WORM_LENGTH, WORM_HEALTH);
        ModUtils.testMessage(level(), "constructor");
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
        return null;
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
        return null;
    }
}
