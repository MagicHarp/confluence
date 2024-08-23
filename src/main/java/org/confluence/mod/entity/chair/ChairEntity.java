package org.confluence.mod.entity.chair;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.block.furniture.chair.AbstractChairBlock;
import org.jetbrains.annotations.NotNull;

public class ChairEntity extends Entity {
    public ChairEntity(EntityType<ChairEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {}

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {}

    @Override
    public void tick() {
        super.tick();
        Block chair = level().getBlockState(this.getOnPos()).getBlock();
        if (this.getPassengers().isEmpty() || !(chair instanceof AbstractChairBlock)) {
            this.discard();
        }
    }

    @Override
    public @NotNull Vec3 getDismountLocationForPassenger(@NotNull LivingEntity passenger) {
        return new Vec3(this.getX(), this.getY() + 1.0D, this.getZ());
    }
}
