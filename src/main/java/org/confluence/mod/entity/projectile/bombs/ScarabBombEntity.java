package org.confluence.mod.entity.projectile.bombs;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;

public class ScarabBombEntity extends StickyBombEntity {
    float blastPowerNew = 3.5f;

    Vec3 facingDir = new Vec3(0, -1, 0);

    public ScarabBombEntity(EntityType<? extends ScarabBombEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        super.blastPower = blastPowerNew;
    }

    public ScarabBombEntity(Level pLevel, LivingEntity pShooter) {
        super(pLevel, pShooter);
        super.blastPower = blastPowerNew;
    }

    public ScarabBombEntity(Level pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);
        super.blastPower = blastPowerNew;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.SCARAB_BOMB.get();
    }

    @Override
    protected void explodeFunction() {
        Vec3 blastPos = getEyePosition();
        Vec3 step = facingDir.normalize();
        for (int i = 0; i < 10; i ++) {
            this.level().explode(this, blastPos.x(), blastPos.y(), blastPos.z(), blastPower, Level.ExplosionInteraction.BLOCK);

            blastPos = blastPos.add(step);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (getOwner() != null) {
            facingDir = getEyePosition().subtract(getOwner().getEyePosition());
            float[] yawPitch = ModUtils.dirToRot(facingDir);
            // 将Yaw和Pitch调整至45的倍数（还原原作.jpg）
            for (int i = 0; i < 2; i ++) {
                // 先+360防止取余运算对负数犯病
                yawPitch[i] += 360f;
                float remainder = yawPitch[i] % 45f;
                yawPitch[i] -= remainder;
                if (remainder > 22.5f)
                    yawPitch[i] += 45f;
            }
            facingDir = ModUtils.rotToDir(yawPitch[0], yawPitch[1]);
        }
        ModUtils.updateEntityRotation(this, facingDir);
    }
}
