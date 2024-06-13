package org.confluence.mod.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.misc.ModDamageTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BoulderEntity extends Projectile {
    public static final double SPEED = 0.70;
    public static final float DIAMETER = 1.0F;
    public float rotateO;
    public float rotate;

    public BoulderEntity(EntityType<BoulderEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.rotateO = 0.0F;
        this.rotate = 0.0F;
    }

    public BoulderEntity(Level level, Vec3 pos) {
        super(ModEntities.BOULDER.get(), level);
        this.rotateO = 0.0F;
        this.rotate = 0.0F;
        setPos(pos);
    }

    public void remove() {
        if (level() instanceof ServerLevel serverLevel) {
            BlockPos pos = getOnPos();
            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.COBBLESTONE.defaultBlockState())
                .setPos(pos), getX(), getY() + 0.5, getZ(), 175, 0.0, 0.0, 0.0, 0.15);
            serverLevel.playSound(null, pos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 5.0F, 1.0F);
        }
        discard();
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 motion = getDeltaMovement();
        setYRot(((float) Mth.atan2(motion.x, motion.z) * Mth.RAD_TO_DEG));
        setDeltaMovement(motion.x, onGround() ? 0.0 : motion.y - 0.08, motion.z);
        move(MoverType.SELF, getDeltaMovement());

        Vec3 delta = getDeltaMovement().scale(0.99);
        setDeltaMovement(delta);
        double s = delta.length();
        double r = 2.0 * s / DIAMETER;
        if (rotate > Mth.TWO_PI) this.rotate -= Mth.TWO_PI;
        this.rotateO = rotate;
        this.rotate += r;

        Vec3 start = position();
        Vec3 end = start.add(delta);
        HitResult hitResult = level().clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        if (hitResult.getType() != HitResult.Type.MISS) end = hitResult.getLocation();
        HitResult hitResult1 = ProjectileUtil.getEntityHitResult(level(), this, start, end, getBoundingBox().expandTowards(delta).inflate(1.0), entity -> true);
        if (hitResult1 != null) hitResult = hitResult1;

        if (hitResult instanceof BlockHitResult blockHitResult) {
            onHitBlock(blockHitResult);
        } else {
            onHitEntity((EntityHitResult) hitResult);
        }
        if (getDeltaMovement().length() < 0.007) remove();
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        if (blockHitResult.getDirection().getAxis() == Direction.Axis.Y) {
            targetTo(level().getNearestPlayer(this, 31.5));
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        entity.hurt(ModDamageTypes.of(entity.level(), ModDamageTypes.BOULDER, this), 100.0F);
    }

    public void targetTo(@Nullable Player player) {
        Vec3 vec3 = player == null ? getDeltaMovement().scale(1.0 + SPEED) : player.position().subtract(position()).normalize();
        setYRot(((float) Mth.atan2(vec3.z, vec3.x)) * Mth.PI);
        setDeltaMovement(vec3.scale(SPEED));
        this.yRotO = getYRot();
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag pCompound) {}

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag pCompound) {}
}
