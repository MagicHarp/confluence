package org.confluence.mod.entity.minion;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.mixinauxiliary.Immunity;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;

public class FinchMinionEntity extends MinionEntity implements Immunity {

    public FinchMinionEntity(Level pLevel) {
        super(ModEntities.FINCH_MINION.get(), pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, 1.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10.00)
                .add(Attributes.MAX_HEALTH, 1.0);
    }

    @Override
    public void tick() {
        setNoGravity(true);
        super.tick();
        if (ownerAttack != null){
            Vec3 direction = ModUtils.getDirection(position(), ownerAttack.position(), 1.8D);
            double[] dirs = {direction.x, direction.y, direction.z};
            setDeltaMovement(dirs[0] / 18.5
                    , dirs[1] / 18.5
                    , dirs[2] / 18.5);
            setRot(-(ModUtils.dirToRot(direction)[0]), -(ModUtils.dirToRot(direction)[1]));
        } else {
            if (owner != null){
                Vec3 direction = ModUtils.getDirection(position(), owner.position(), 1.8D);
                double[] dirs = {direction.x, direction.y, direction.z};
                setDeltaMovement(dirs[0] / 20.5, dirs[1] / 20.5, dirs[2] / 20.5);
                setRot(-(ModUtils.dirToRot(direction)[0]), -(ModUtils.dirToRot(direction)[1]));
            }
        }
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (level() instanceof ServerLevel){
            float damage = 3.8F;
            entity.hurt(damageSources().mobProjectile(this, owner), damage);
            RandomSource random = level().random;
            setDeltaMovement(random.nextFloat() / 2, random.nextFloat() / 2, random.nextFloat() / 2);
        }
    }

    @Override
    public Types confluence$getImmunityType(){
        return Types.LOCAL;
    }

    @Override
    public int confluence$getImmunityDuration(){
        return 7;
    }
}
