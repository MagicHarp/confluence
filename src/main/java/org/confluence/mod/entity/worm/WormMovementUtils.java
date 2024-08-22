package org.confluence.mod.entity.worm;

import net.minecraft.world.phys.Vec3;
import org.confluence.mod.util.ModUtils;

import java.util.List;

/**
 * 长直类实体的通用移动逻辑
 */
public class WormMovementUtils {
    /**
     * 长直移动的特性细节
     */
    public static class WormSegmentMovementOptions {
        /** 试图”拉直“的程度，貌似会导致长直反向盘成一团？ */
        double straighteningMultiplier = 0;
        /** “向前跟随”的程度 */
        double followingMultiplier = 1;
        /**  跟随距离 */
        double followDistance = 1;
        /** 使用速度还是直接TP长直实体？ */
        boolean velocityOrTeleport = true;

        /** 试图”拉直“的程度，貌似会导致长直反向盘成一团？ */
        public WormSegmentMovementOptions setStraighteningMultiplier(double straighteningMultiplier) {
            this.straighteningMultiplier = straighteningMultiplier;
            return this;
        }
        /** “向前跟随”的程度 */
        public WormSegmentMovementOptions setFollowingMultiplier(double followingMultiplier) {
            this.followingMultiplier = followingMultiplier;
            return this;
        }
        /**  跟随距离 */
        public WormSegmentMovementOptions setFollowDistance(double followDistance) {
            this.followDistance = followDistance;
            return this;
        }
        /**
         * 使用速度(true)还是直接TP(false)长直实体？
         * 客户端可能以略微不同的方式渲染
         * 长直死亡后体节会生草地甩出去（速度）还是会分头行动（TP且头部AI以速度方式移动）
         * */
        public WormSegmentMovementOptions setVelocityOrTeleport(boolean velocityOrTeleport) {
            this.velocityOrTeleport = velocityOrTeleport;
            return this;
        }
    }


    /**
     * 判定体节移动，此方法默认segments[0]是头部且所有体节都应跟随它
     * */
    public static void handleSegmentsFollow(List<BaseWormPart<?>> segments, WormSegmentMovementOptions moveOption) {
        handleSegmentsFollow(segments, moveOption, 0);
    }
    /**
     * 判定体节移动，此方法指定头部的index且所有体节都应跟随它
     * */
    public static void handleSegmentsFollow(List<BaseWormPart<?>> segments, WormSegmentMovementOptions moveOption, int startIndex) {
        handleSegmentsFollow(segments, moveOption, startIndex, segments.size());
    }
    /**
     * 判定体节移动，此方法指定头部和尾节后的index
     * @param endIndex 尾节的index + 1
     * */
    public static void handleSegmentsFollow(List<BaseWormPart<?>> segments, WormSegmentMovementOptions moveOption,
                                            int startIndex, int endIndex) {
        // 会直接跳过startIndex
        for (int i = startIndex + 1; i < endIndex; i++) {
            BaseWormPart<?> segmentCurrent = segments.get(i);
            if (!segmentCurrent.isAlive()) { // 遇到被撸爆的体节就停下
                return;
            }

            BaseWormPart<?> segmentLast = segments.get(i - 1); // 上一节
            BaseWormPart<?> segmentNext; // 下一节
            Vec3 segDVec; // 方向向量

            // 尾节，体节间的方向设为上一节-本节
            if (segmentCurrent.segmentType == BaseWormPart.SegmentType.TAIL || i + 1 >= endIndex) {
                segDVec = ModUtils.getDirection(segmentCurrent.position(), segmentLast.position(), 1.0);
            }
            // 非尾节，体节间的方向设为上一节-下一节
            else {
                segmentNext = segments.get(i + 1);
                segDVec = ModUtils.getDirection(segmentNext.position(), segmentLast.position(), 1.0);
            }

            Vec3 followDir = ModUtils.getDirection(segmentCurrent.position(), segmentLast.position(), 1.0);
            // 合并拉直和跟随的方向
            Vec3 dVec = segDVec.scale(moveOption.straighteningMultiplier)
                    .add(followDir.scale(moveOption.followingMultiplier));

            // 体节移动/朝向更新
            if (dVec.lengthSqr() > 1e-9) {
                dVec.normalize().scale(moveOption.followDistance);
                Vec3 targetLoc = segmentLast.position().subtract(dVec);
                // 移动
                if (moveOption.velocityOrTeleport) {
                    Vec3 velocity = targetLoc.subtract(segmentCurrent.position());
                    segmentCurrent.setDeltaMovement(velocity);
                }
                // TP
                else {
                    segmentCurrent.teleportTo(targetLoc.x(), targetLoc.y(), targetLoc.z());
                    segmentCurrent.setDeltaMovement(Vec3.ZERO);
                }
                // 视角
                ModUtils.updateEntityRotation(segmentCurrent, dVec);
            }
        }
    }
}
