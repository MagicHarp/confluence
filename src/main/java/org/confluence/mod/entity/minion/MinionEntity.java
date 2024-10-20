package org.confluence.mod.entity.minion;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MinionEntity extends LivingEntity {
    public LivingEntity ownerAttack;
    public LivingEntity owner;

    public MinionEntity(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void tick() {
        super.tick();
        noPhysics = true;
        checkCollision();
        if (owner == null || owner.isDeadOrDying()){
            kill();
        }
    }

    private void checkCollision() {
        HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, entity -> entity.equals(ownerAttack));
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            onHitEntity((EntityHitResult) hitResult);
        }
    }

    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {}

    @Override
    public Iterable<ItemStack> getArmorSlots() { return new ArrayList<>(); }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) { return ItemStack.EMPTY; }

    @Override
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {}

    @Override
    public HumanoidArm getMainArm() { return null; }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    @Override
    public void kill() {
        this.remove(RemovalReason.DISCARDED);
    }
}
