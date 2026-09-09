package org.confluence.mod.common.entity.animal;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.common.entity.ai.bt.BTNode;
import org.confluence.mod.common.entity.ai.bt.BTRoot;
import org.confluence.mod.common.entity.ai.bt.BTStatus;
import org.confluence.mod.common.entity.ai.bt.composite.SelectorNode;
import org.confluence.mod.common.entity.ai.bt.leaf.VanillaGoalAction;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.List;

/**
 * 能沿地面、墙面和天花板连续爬行的蜗牛类小动物。
 */
public class Snail extends SimpleCritter {
    private static final RawAnimation CRAWL = RawAnimation.begin().thenLoop("move.walk");
    private static final String ATTACHMENT_TAG = "SnailAttachment";
    private static final String CRAWL_DIRECTION_TAG = "SnailCrawlDirection";
    private static final EntityDataAccessor<Byte> DATA_ATTACHMENT = SynchedEntityData.defineId(Snail.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Byte> DATA_CRAWL_DIRECTION = SynchedEntityData.defineId(Snail.class, EntityDataSerializers.BYTE);
    private final Profile profile;
    private int turnCooldown;
    private int blockedTicks;
    private Vec3 lastCrawlPosition;
    private boolean attachmentNeedsValidation = true;

    public Snail(EntityType<? extends Snail> type, Level level) {
        this(type, level, Profile.NORMAL);
    }

    public Snail(EntityType<? extends Snail> type, Level level, Profile profile) {
        super(type, level);
        this.profile = profile;
        // 高度变化由贴面爬行处理，禁用原版自动跨步，避免跳上台阶。
        setMaxUpStep(0.0F);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // GeckoLib 的通用行走控制器只可靠识别地面水平位移；沿墙竖直爬行会被误判为静止。
        controllers.add(new AnimationController<>(this, "Crawl", 0,
                state -> isNoGravity() ? state.setAndContinue(CRAWL) : PlayState.STOP));
    }

    @Override
    public boolean fireImmune() {
        return profile.fireImmune;
    }

    @Override
    public boolean isFullBright() {
        return profile.fullBright;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_ATTACHMENT, (byte) Direction.UP.get3DDataValue());
        entityData.define(DATA_CRAWL_DIRECTION, (byte) Direction.NORTH.get3DDataValue());
    }

    @Override
    public boolean onClimbable() {
        // 原版梯子逻辑会在碰墙时将下降速度改成向上 0.2，不能用于贴面爬行。
        return false;
    }

    public Direction getAttachmentFace() {
        return Direction.from3DDataValue(Byte.toUnsignedInt(entityData.get(DATA_ATTACHMENT)));
    }

    public Direction getCrawlDirection() {
        return Direction.from3DDataValue(Byte.toUnsignedInt(entityData.get(DATA_CRAWL_DIRECTION)));
    }

    @Override
    protected BTRoot createBT() {
        return new BTRoot() {
            @Override
            protected BTNode createTree() {
                return SelectorNode.of(
                        new VanillaGoalAction(new FloatGoal(Snail.this)),
                        new SurfaceCrawlAction());
            }
        };
    }

    private void setAttachmentFace(Direction face) {
        entityData.set(DATA_ATTACHMENT, (byte) face.get3DDataValue());
    }

    private void setCrawlDirection(Direction direction) {
        entityData.set(DATA_CRAWL_DIRECTION, (byte) direction.get3DDataValue());
    }

    private boolean hasSupport(Direction face) {
        return hasSupport(face, getBoundingBox());
    }

    private boolean hasSupport(Direction face, AABB bounds) {
        double thickness = 0.08D;
        double insetX = Math.min(0.08D, bounds.getXsize() * 0.2D);
        double insetY = Math.min(0.08D, bounds.getYsize() * 0.2D);
        double insetZ = Math.min(0.08D, bounds.getZsize() * 0.2D);
        AABB probe = switch (face) {
            case UP -> new AABB(bounds.minX + insetX, bounds.minY - thickness, bounds.minZ + insetZ,
                    bounds.maxX - insetX, bounds.minY + 0.01D, bounds.maxZ - insetZ);
            case DOWN -> new AABB(bounds.minX + insetX, bounds.maxY - 0.01D, bounds.minZ + insetZ,
                    bounds.maxX - insetX, bounds.maxY + thickness, bounds.maxZ - insetZ);
            case EAST ->
                    new AABB(bounds.minX - thickness, bounds.minY + insetY, bounds.minZ + insetZ,
                            bounds.minX + 0.01D, bounds.maxY - insetY, bounds.maxZ - insetZ);
            case WEST -> new AABB(bounds.maxX - 0.01D, bounds.minY + insetY, bounds.minZ + insetZ,
                    bounds.maxX + thickness, bounds.maxY - insetY, bounds.maxZ - insetZ);
            case SOUTH ->
                    new AABB(bounds.minX + insetX, bounds.minY + insetY, bounds.minZ - thickness,
                            bounds.maxX - insetX, bounds.maxY - insetY, bounds.minZ + 0.01D);
            case NORTH -> new AABB(bounds.minX + insetX, bounds.minY + insetY, bounds.maxZ - 0.01D,
                    bounds.maxX - insetX, bounds.maxY - insetY, bounds.maxZ + thickness);
        };
        return level().getBlockCollisions(this, probe).iterator().hasNext();
    }

    private Direction findPhysicalSupport(Direction preferred) {
        if (hasSupport(Direction.UP)) return Direction.UP;
        if (preferred != Direction.UP && hasSupport(preferred)) return preferred;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (direction != preferred && hasSupport(direction)) return direction;
        }
        return preferred != Direction.DOWN && hasSupport(Direction.DOWN) ? Direction.DOWN : null;
    }

    private Direction findAdjacentSupport() {
        Direction current = getAttachmentFace();
        Direction crawl = getCrawlDirection();
        if (current.getAxis().isHorizontal()) {
            if (crawl == Direction.DOWN && hasSupport(Direction.UP)) return Direction.UP;
            if (crawl == Direction.UP && hasSupport(Direction.DOWN)) return Direction.DOWN;
        }
        if (hasSupport(current)) return current;
        if (hasSupport(Direction.UP)) return Direction.UP;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (direction != current && hasSupport(direction)) return direction;
        }
        return hasSupport(Direction.DOWN) ? Direction.DOWN : null;
    }

    private void ensureTangentDirection(Direction attachment) {
        if (getCrawlDirection().getAxis() != attachment.getAxis()) return;
        setCrawlDirection(attachment.getAxis().isVertical() ? Direction.Plane.HORIZONTAL.getRandomDirection(random) : random.nextBoolean() ? Direction.UP : Direction.DOWN);
    }

    private void updateWanderingDirection(Direction attachment) {
        Vec3 currentPosition = position();
        if (lastCrawlPosition != null && currentPosition.distanceToSqr(lastCrawlPosition) < 1.0E-5D)
            blockedTicks++;
        else blockedTicks = 0;
        lastCrawlPosition = currentPosition;

        if (blockedTicks >= 12) {
            setCrawlDirection(selectAvoidanceDirection(attachment));
            blockedTicks = 0;
            turnCooldown = 30 + random.nextInt(91);
            return;
        }
        if (--turnCooldown > 0) return;
        // 墙面和天花板上保持当前方向直到抵达棱边；中途随机反向会让蜗牛长期困在同一侧。
        if (attachment != Direction.UP) {
            turnCooldown = 50 + random.nextInt(151);
            return;
        }
        Direction current = getCrawlDirection();
        List<Direction> candidates = java.util.Arrays.stream(Direction.values())
                .filter(direction -> direction.getAxis() != attachment.getAxis())
                .filter(direction -> canAdvance(attachment, direction))
                .toList();
        if (!candidates.isEmpty()) {
            int totalWeight = 0;
            for (Direction direction : candidates)
                totalWeight += direction == current ? 4 : direction == current.getOpposite() ? 1 : 2;
            int selected = random.nextInt(totalWeight);
            for (Direction direction : candidates) {
                selected -= direction == current ? 4 : direction == current.getOpposite() ? 1 : 2;
                if (selected < 0) {
                    setCrawlDirection(direction);
                    break;
                }
            }
        }
        turnCooldown = 50 + random.nextInt(151);
    }

    private Direction selectAvoidanceDirection(Direction attachment) {
        Direction current = getCrawlDirection();
        Direction reverse = current.getOpposite();
        List<Direction> detours = java.util.Arrays.stream(Direction.values())
                .filter(direction -> direction.getAxis() != attachment.getAxis())
                .filter(direction -> direction != current && direction != reverse)
                .filter(direction -> canAdvance(attachment, direction))
                .toList();
        if (!detours.isEmpty()) return detours.get(random.nextInt(detours.size()));
        return canAdvance(attachment, reverse) ? reverse : current;
    }

    private boolean canAdvance(Direction attachment, Direction direction) {
        Vec3 step = Vec3.atLowerCornerOf(direction.getNormal()).scale(Math.max(0.08D, getBbWidth() * 0.25D));
        AABB movedBounds = getBoundingBox().move(step);
        if (!level().noCollision(this, movedBounds)) return false;
        // 保持当前表面，或越过外棱后能够贴到前进方向对应的相邻表面。
        return hasSupport(attachment, movedBounds) || hasSupport(direction, movedBounds);
    }

    private void transitionToAdjacentSupport(Direction previousAttachment, Direction attachment, Direction crawl) {
        setAttachmentFace(attachment);
        if (previousAttachment.getAxis().isVertical()) {
            if (attachment == crawl) setCrawlDirection(previousAttachment.getOpposite());
            return;
        }
        if (attachment == Direction.UP)
            setCrawlDirection(crawl == Direction.UP ? previousAttachment.getOpposite() : previousAttachment);
        else if (attachment == Direction.DOWN)
            setCrawlDirection(previousAttachment.getOpposite());
    }

    private void turnOntoObstacle(Direction attachment, Direction crawl) {
        boolean obstacleAhead = crawl.getAxis().isHorizontal() && moveUpToObstacle(crawl);
        Direction obstacleFace = crawl.getOpposite();
        boolean canAttachToObstacle = obstacleAhead && hasSupport(obstacleFace);
        if (attachment == Direction.UP && crawl.getAxis().isHorizontal() && canAttachToObstacle) {
            setAttachmentFace(crawl.getOpposite());
            setCrawlDirection(Direction.UP);
        } else if (attachment == Direction.DOWN && crawl.getAxis().isHorizontal() && canAttachToObstacle) {
            setAttachmentFace(crawl.getOpposite());
            setCrawlDirection(Direction.DOWN);
        } else if (attachment.getAxis().isHorizontal()
                && crawl.getAxis().isHorizontal()
                && canAttachToObstacle) {
            // 沿墙横爬撞到相邻墙面时绕过内棱；否则会一直顶着拐角不动。
            setAttachmentFace(crawl.getOpposite());
            setCrawlDirection(attachment.getOpposite());
        }
    }

    private boolean moveUpToObstacle(Direction crawl) {
        Vec3 approach = Vec3.atLowerCornerOf(crawl.getNormal()).scale(Math.max(0.08D, getBbWidth() * 0.25D));
        if (level().noCollision(this, getBoundingBox().move(approach))) return false;
        // 使用实体自身的碰撞移动贴到表面，而不是扩大支撑探针造成悬空贴附。
        move(MoverType.SELF, approach);
        return true;
    }

    private void updateCrawlRotation(Direction crawl, Direction attachment) {
        Vec3 direction = Vec3.atLowerCornerOf(crawl.getNormal());
        double horizontalLength = direction.horizontalDistance();
        Direction yawDirection = horizontalLength > 1.0E-4D ? crawl : crawl == Direction.UP ? attachment.getOpposite() : attachment;
        float yaw = yawFor(yawDirection);
        float pitch = (float) (-Mth.atan2(direction.y, horizontalLength) * Mth.RAD_TO_DEG);
        setYRot(yaw);
        setXRot(pitch);
        setYBodyRot(yaw);
        setYHeadRot(yaw);
        Vec3 eye = getEyePosition();
        getLookControl().setLookAt(eye.x + direction.x, eye.y + direction.y, eye.z + direction.z, 180.0F, 180.0F);
    }

    private static float yawFor(Direction direction) {
        return switch (direction) {
            case SOUTH -> 0.0F;
            case WEST -> 90.0F;
            case NORTH -> 180.0F;
            case EAST -> -90.0F;
            default -> 0.0F;
        };
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putByte(ATTACHMENT_TAG, entityData.get(DATA_ATTACHMENT));
        tag.putByte(CRAWL_DIRECTION_TAG, entityData.get(DATA_CRAWL_DIRECTION));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains(ATTACHMENT_TAG))
            entityData.set(DATA_ATTACHMENT, tag.getByte(ATTACHMENT_TAG));
        if (tag.contains(CRAWL_DIRECTION_TAG))
            entityData.set(DATA_CRAWL_DIRECTION, tag.getByte(CRAWL_DIRECTION_TAG));
        setNoGravity(false);
        attachmentNeedsValidation = true;
        blockedTicks = 0;
        lastCrawlPosition = null;
    }

    private final class SurfaceCrawlAction extends BTNode {
        @Override
        public void start() {
            navigation.stop();
            ensureTangentDirection(getAttachmentFace());
            turnCooldown = 30 + random.nextInt(121);
            lastCrawlPosition = position();
        }

        @Override
        public BTStatus execute() {
            if (isInWaterOrBubble() || isInLava()) {
                setNoGravity(false);
                return BTStatus.FAILURE;
            }
            if (attachmentNeedsValidation) {
                Direction recovered = findPhysicalSupport(getAttachmentFace());
                attachmentNeedsValidation = false;
                if (recovered == null) {
                    setAttachmentFace(Direction.UP);
                    setNoGravity(false);
                    return BTStatus.RUNNING;
                }
                setAttachmentFace(recovered);
                ensureTangentDirection(recovered);
            }
            Direction support = findAdjacentSupport();
            if (support == null) {
                setNoGravity(false);
                return BTStatus.RUNNING;
            }
            Direction previousAttachment = getAttachmentFace();
            Direction previousCrawl = getCrawlDirection();
            if (support != previousAttachment)
                transitionToAdjacentSupport(previousAttachment, support, previousCrawl);
            ensureTangentDirection(support);
            Direction crawl = getCrawlDirection();
            turnOntoObstacle(support, crawl);
            support = getAttachmentFace();
            crawl = getCrawlDirection();
            if (!canAdvance(support, crawl)) {
                setCrawlDirection(selectAvoidanceDirection(support));
            }
            updateWanderingDirection(support);
            crawl = getCrawlDirection();
            setNoGravity(true);
            fallDistance = 0.0F;
            double speed = getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.18;
            Vec3 tangent = Vec3.atLowerCornerOf(crawl.getNormal()).scale(speed);
            Vec3 adhesion = Vec3.atLowerCornerOf(support.getOpposite().getNormal()).scale(0.025);
            setDeltaMovement(canAdvance(support, crawl) ? tangent.add(adhesion) : adhesion);
            updateCrawlRotation(crawl, support);
            hasImpulse = true;
            return BTStatus.RUNNING;
        }

        @Override
        public void stop() {
            setNoGravity(false);
        }
    }

    /// 共享爬行状态机的蜗牛物种能力，不包含纹理、属性或生成权重。
    public enum Profile {
        NORMAL(false, false),
        GLOWING(false, true),
        MAGMA(true, true);

        private final boolean fireImmune;
        private final boolean fullBright;

        Profile(boolean fireImmune, boolean fullBright) {
            this.fireImmune = fireImmune;
            this.fullBright = fullBright;
        }
    }
}
