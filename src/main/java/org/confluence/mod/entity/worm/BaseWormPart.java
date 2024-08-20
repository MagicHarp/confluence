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

public class BaseWormPart<E extends AbstractWormEntity> extends PartEntity<E> {
    protected static final EntityDataAccessor<Float> DATA_HEALTH_ID = SynchedEntityData.defineId(BaseWormPart.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Vector3f> DATA_DIR_ID = SynchedEntityData.defineId(BaseWormPart.class, EntityDataSerializers.VECTOR3);
    private final float maxHealth;
    protected final AbstractWormEntity parentMob;

    public BaseWormPart(E parent, float maxHealth) {
        super(parent);
        this.maxHealth = maxHealth;
        this.parentMob = parent;
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
}
