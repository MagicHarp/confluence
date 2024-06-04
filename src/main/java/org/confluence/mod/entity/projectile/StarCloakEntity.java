package org.confluence.mod.entity.projectile;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.misc.ModDamageTypes;
import org.confluence.mod.util.PlayerUtils;
import org.jetbrains.annotations.NotNull;

public class StarCloakEntity extends Projectile {
    private static final EntityDataAccessor<Boolean> DATA_MANA = SynchedEntityData.defineId(StarCloakEntity.class, EntityDataSerializers.BOOLEAN);
    private final boolean hasMana;
    private int age;

    public StarCloakEntity(EntityType<StarCloakEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.hasMana = false;
    }

    public StarCloakEntity(Level level, LivingEntity living, Entity target, boolean hasMana) {
        super(ModEntities.STAR_CLOAK.get(), level);
        this.hasMana = hasMana;
        setOwner(living);
        setPos(target.position().add(0.0, 32.0, 0.0));
        setDeltaMovement(0.0, -2.0, 0.0);
        setNoGravity(true);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(DATA_MANA, false);
    }

    @Override
    public void tick() {
        if (getOwner() == null) {
            discard();
            return;
        }
        super.tick();
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        Vec3 vec3 = getDeltaMovement();
        HitResult.Type hitresult$type = hitresult.getType();
        if (hitresult$type == HitResult.Type.BLOCK) {
            if (hasMana) {
                entityData.set(DATA_MANA, true);
            } else {
                discard();
                return;
            }
        }
        if (hitresult.getLocation().y < getOwner().getY()) {
            discard();
            return;
        }
        if (hitresult$type == HitResult.Type.ENTITY) {
            onHitEntity((EntityHitResult) hitresult);
        }
        boolean mana = isManaState();
        if (mana) {
            addDeltaMovement(getOwner().position().subtract(position()).normalize().scale(0.05));
        }
        double offX = getX() + vec3.x;
        double offY = getY() + vec3.y;
        double offZ = getZ() + vec3.z;
        setPos(offX, offY, offZ);
        if ((mana && age++ > 100) || tickCount > 200) {
            discard();
        }
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        if (isManaState()) {
            if (pResult.getEntity() instanceof ServerPlayer serverPlayer) {
                PlayerUtils.receiveMana(serverPlayer, () -> 50);
            }
        } else {
            pResult.getEntity().hurt(ModDamageTypes.of(level(), ModDamageTypes.STAR_CLOAK, getOwner()), 10.0F);
        }
    }

    @NotNull
    private Boolean isManaState() {
        return entityData.get(DATA_MANA);
    }

    @Override
    protected boolean canHitEntity(@NotNull Entity pTarget) {
        if (!pTarget.canBeHitByProjectile()) {
            return false;
        } else if (isManaState()) {
            return pTarget == getOwner();
        } else {
            Entity owner = getOwner();
            return pTarget != owner || !owner.isPassengerOfSameVehicle(pTarget);
        }
    }
}
