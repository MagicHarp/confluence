package org.confluence.mod.entity.worm;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.entity.PartEntity;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;

public class BaseWormPart<E extends AbstractWormEntity> extends PartEntity<E> {
    public enum SegmentType {
        HEAD, BODY, TAIL;
    }
    protected static final EntityDataAccessor<Float> DATA_HEALTH_ID = SynchedEntityData.defineId(BaseWormPart.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Vector3f> DATA_DIR_ID = SynchedEntityData.defineId(BaseWormPart.class, EntityDataSerializers.VECTOR3);
    private final float maxHealth;
    protected final AbstractWormEntity parentMob;
    // 体节在wormParts中的index
    protected final int segmentIndex;
    // 不要给final，世吞可以被打断
    protected SegmentType segmentType;

    public BaseWormPart(E parent, int segmentIndex, float maxHealth) {
        super(parent);
        this.parentMob = parent;
        this.segmentIndex = segmentIndex;
        this.maxHealth = maxHealth;
        setHealth(maxHealth);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(DATA_HEALTH_ID, 1.0F);
        entityData.define(DATA_DIR_ID, new Vector3f(0.0F));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {}

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
        entityData.set(DATA_HEALTH_ID, nbt.getFloat("Health"));
        entityData.set(DATA_DIR_ID, new Vector3f(
            nbt.getFloat("DirX"),
            nbt.getFloat("DirY"),
            nbt.getFloat("DirZ")
        ));
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = super.serializeNBT();
        compoundTag.putFloat("Health", entityData.get(DATA_HEALTH_ID));
        Vector3f vector3f = entityData.get(DATA_DIR_ID);
        compoundTag.putFloat("DirX", vector3f.x);
        compoundTag.putFloat("DirY", vector3f.y);
        compoundTag.putFloat("DirZ", vector3f.z);
        return compoundTag;
    }

    public float getHealth() {
        return entityData.get(DATA_HEALTH_ID);
    }

    public void setHealth(float pHealth) {
        this.entityData.set(DATA_HEALTH_ID, Mth.clamp(pHealth, 0.0F, getMaxHealth()));
    }

    public final float getMaxHealth() {
        return maxHealth;
    }

    // “可被选中”
    public boolean isPickable() {
        return true;
    }

    @Nullable
    public ItemStack getPickResult() {
        return this.parentMob.getPickResult();
    }

    public boolean is(Entity pEntity) {
        return this == pEntity || this.parentMob == pEntity;
    }

    public boolean shouldBeSaved() {
        return false;
    }

    /** 更新体节类型，对于世界吞噬者可在segmentAI中重复调用，而对于一般蠕虫在AbstractWormEntity的构造器中调用即可 */
    protected void updateSegmentType() {
        SegmentType result;
        if (segmentIndex == 0 || ! ((List<BaseWormPart<?>>) parentMob.wormParts).get(segmentIndex - 1).isAlive()) {
            result = SegmentType.HEAD;
        } else if (segmentIndex + 1 > ((List<BaseWormPart<?>>) parentMob.wormParts).size() || ! ((List<BaseWormPart<?>>) parentMob.wormParts).get(segmentIndex + 1).isAlive()) {
            result = SegmentType.TAIL;
        } else {
            result = SegmentType.BODY;
        }
        this.segmentType = result;
    }
    // 覆盖此方法以实现额外AI，如头部移动/体节发射弹幕等
    protected void tickSegment() {
        updateSegmentType();
    }
}
