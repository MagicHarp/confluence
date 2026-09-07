package org.confluence.mod.common.entity.monster;

import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public interface WormSegment {
    int getSegmentIndex();
    @Nullable WormSegment getPrev();
    @Nullable WormSegment getNext();
    default boolean isTail() {return false;}

    static boolean isWormDamage(DamageSource source) {
        // 同时检查攻击主体和射弹所有者，体节转发及弹幕伤害不能绕过同类免伤。
        return source.getEntity() instanceof WormSegment || source.getDirectEntity() instanceof WormSegment
                || source.getDirectEntity() instanceof Projectile projectile && projectile.getOwner() instanceof WormSegment;
    }

    static void orientAlong(Entity segment, Vec3 tangent) {
        if (tangent.lengthSqr() < 1.0E-7) return;
        double horizontal = tangent.horizontalDistance();
        float yaw = horizontal < 1.0E-5 ? segment.getYRot() : (float) (Mth.atan2(tangent.z, tangent.x) * Mth.RAD_TO_DEG) - 90.0F;
        float pitch = (float) (-Mth.atan2(tangent.y, horizontal) * Mth.RAD_TO_DEG);
        // 固定使用同一组姿态：偏航加半圈、俯仰取补角虽然保持前进方向，却会翻转背腹。
        // 各节不能按自己的历史角度选择这种“等价方向”；跨偏航边界由客户端最短角插值处理。
        segment.setYRot(yaw);
        segment.setXRot(pitch);
    }

}
