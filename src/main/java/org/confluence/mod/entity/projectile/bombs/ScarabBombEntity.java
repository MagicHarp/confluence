package org.confluence.mod.entity.projectile.bombs;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;

public class ScarabBombEntity extends StickyBombEntity {
    private static final float BLAST_POWER_NEW = 3.5f;
    private Vec3 facingDir = new Vec3(0, -1, 0);

    public ScarabBombEntity(EntityType<? extends ScarabBombEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.blastPower = BLAST_POWER_NEW;
    }

    public ScarabBombEntity(Level pLevel, double pX, double pY, double pZ) {
        super(ModEntities.SCARAB_BOMB_ENTITY.get(), pX, pY, pZ, pLevel);
        this.blastPower = BLAST_POWER_NEW;
    }

    public ScarabBombEntity(LivingEntity pShooter) {
        super(ModEntities.SCARAB_BOMB_ENTITY.get(), pShooter);
        this.blastPower = BLAST_POWER_NEW;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.SCARAB_BOMB.get();
    }

    @Override
    protected void explodeFunction() {
        Vec3 blastPos = getEyePosition();
        Vec3 step = facingDir.normalize();
        for (int i = 0; i < 10; i++) {
            level().explode(this, blastPos.x(), blastPos.y(), blastPos.z(), blastPower, Level.ExplosionInteraction.BLOCK);
            blastPos = blastPos.add(step);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (getOwner() != null) {
            this.facingDir = getEyePosition().subtract(getOwner().getEyePosition());
            float[] yawPitch = ModUtils.dirToRot(facingDir);
            // 将Yaw和Pitch调整至45的倍数（还原原作.jpg）
            for (int i = 0; i < 2; i++) {
                // 先+360防止取余运算对负数犯病
                yawPitch[i] += 360f;
                float remainder = yawPitch[i] % 45f;
                yawPitch[i] -= remainder;
                if (remainder > 22.5f)
                    yawPitch[i] += 45f;
            }
            this.facingDir = ModUtils.rotToDir(yawPitch[0], yawPitch[1]);
        }
        ModUtils.updateEntityRotation(this, facingDir);
    }
}
