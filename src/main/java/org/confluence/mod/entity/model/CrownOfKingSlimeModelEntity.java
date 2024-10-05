package org.confluence.mod.entity.model;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class CrownOfKingSlimeModelEntity extends Entity {
    public float rotate = 0.0F;
    public float rotateO = 0.0F;

    public CrownOfKingSlimeModelEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {}

    @Override
    public void tick() {

    }
}
