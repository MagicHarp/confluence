package org.confluence.mod.entity.worm;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

// 不要用PartEntity，PartEntity不是LivingEntity，连移动功能都没有
public class BaseWormPart<E extends AbstractWormEntity> extends Mob {
    public enum SegmentType {
        HEAD, BODY, TAIL;
    }
    private float maxHealth = 10;
    private AbstractWormEntity parentMob;
    public AbstractWormEntity getParentMob() {return parentMob;}
    // 体节在wormParts中的index
    private int segmentIndex;
    public int getSegmentIndex() {return segmentIndex;}
    // 不要给final，世吞可以被打断
    protected SegmentType segmentType;
    public SegmentType getSegmentType() {return segmentType;}

    public BaseWormPart(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    public void setInfo(E parentMob, int segmentIndex, float maxHealth) {
        this.parentMob = parentMob;
        this.segmentIndex = segmentIndex;
        this.maxHealth = maxHealth;
        setHealth(maxHealth);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        // 不会窒息
        if (pSource == damageSources().inWall()) {
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public boolean isPushable(){
        return false;
    }
    @Override
    public void push(@NotNull Entity pEntity){
    }
    @Override
    protected void pushEntities(){
    }

    @Override
    public void onAddedToWorld(){
        super.onAddedToWorld();
        // 不会撞墙等等
        setNoGravity(true);
        this.noPhysics = true;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = super.serializeNBT();
        return compoundTag;
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

//    public final float getMaxHealth() {
//        return MAX_HEALTH;
//    }

    @Override
    public HumanoidArm getMainArm() {
        return null;
    }

    // 创造模式选中
    public boolean isPickable() {
        return true;
    }
/*    @Nullable
    public ItemStack getPickResult() {
        return this.parentMob.getPickResult();
    }*/

    public boolean is(Entity pEntity) {
        return this == pEntity || this.parentMob == pEntity;
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    /** 更新体节类型，对于世界吞噬者可在segmentAI中重复调用，而对于一般蠕虫在AbstractWormEntity的构造器中调用即可 */
    protected void updateSegmentType() {
        SegmentType result;
        if (segmentIndex == 0 || ! ((List<BaseWormPart<?>>) parentMob.wormParts).get(segmentIndex - 1).isAlive()) {
            result = SegmentType.HEAD;
        } else if (segmentIndex + 1 >= ((List<BaseWormPart<?>>) parentMob.wormParts).size() || ! ((List<BaseWormPart<?>>) parentMob.wormParts).get(segmentIndex + 1).isAlive()) {
            result = SegmentType.TAIL;
        } else {
            result = SegmentType.BODY;
        }
        this.segmentType = result;
    }
    // 覆盖此方法以实现额外AI，如头部移动/体节发射弹幕等
    protected void tickSegment() {
        // 蠕虫实体不存在/自己不再是这一体节后死掉
        if (parentMob == null ||
                parentMob.wormParts == null ||
                parentMob.wormParts.size() <= segmentIndex ||
                parentMob.wormParts.get(segmentIndex) != this) {
            this.discard();
        }

        updateSegmentType();
    }

    @Override
    public void tick() {
        super.tick();
    }


}
