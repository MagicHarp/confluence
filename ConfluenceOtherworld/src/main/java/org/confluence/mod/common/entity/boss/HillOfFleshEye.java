package org.confluence.mod.common.entity.boss;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.common.entity.projectile.HostileParticleProjectile;
import org.confluence.mod.common.init.entity.ModEntities;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

/// 肉丘之眼——定位在肉丘周围，向环形范围内的玩家射击。
public class HillOfFleshEye extends BaseBossPart<HillOfFlesh> implements GeoEntity {
    private static final float DAMAGE = 10.0F;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int shootDelay;
    private int shootBurst;

    public HillOfFleshEye(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public void setMaster(HillOfFlesh master) {
        bindTo(master);
        this.shootDelay = 30 + random.nextInt(40);
    }

    @Override
    protected void tickPart(HillOfFlesh master) {
        if (level().isClientSide) return;
        if (master.isInitializing()) return;

        LivingEntity target = master.findTargetForPart(position());
        if (target != null) {
            shootDelay--;
            if (shootDelay <= 0) {
                shootBurst++;
                shootAt(master, target);
                if (shootBurst >= 3) {
                    shootBurst = 0;
                    shootDelay = (master.isPhase2() ? 25 : 40) + random.nextInt(20);
                } else {
                    shootDelay = 8;
                }
            }
        }
    }

    private void shootAt(HillOfFlesh master, LivingEntity target) {
        if (!(level() instanceof ServerLevel serverLevel)) return;
        Vec3 origin = getBoundingBox().getCenter();
        Vec3 dir = target.getEyePosition().subtract(origin);
        if (dir.lengthSqr() < 0.01) return;
        HostileParticleProjectile projectile = ModEntities.HILL_FIRE_BOUND.get().create(serverLevel);
        if (projectile == null) return;
        double speed = shootBurst >= 3 ? 1.0 : 0.5;
        projectile.configure(master, origin, dir.normalize().scale(speed), DAMAGE, 100);
        serverLevel.addFreshEntity(projectile);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return hurtOwnerAndPart(source, amount, 2.0F);
    }

    @Override
    protected Class<HillOfFlesh> getOwnerType() {
        return HillOfFlesh.class;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
