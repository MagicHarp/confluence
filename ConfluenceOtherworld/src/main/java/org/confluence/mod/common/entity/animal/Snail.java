package org.confluence.mod.common.entity.animal;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.common.entity.ai.bt.BTNode;
import org.confluence.mod.common.entity.ai.bt.BTRoot;
import org.confluence.mod.common.entity.ai.bt.BTStatus;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class Snail extends SimpleCritter {
    private static final RawAnimation CRAWL = RawAnimation.begin().thenLoop("move.walk");
    private static final String ATTACHMENT_TAG = "SnailAttachment";
    private static final String CRAWL_DIRECTION_TAG = "SnailCrawlDirection";
    private static final EntityDataAccessor<Byte> DATA_ATTACHMENT = SynchedEntityData.defineId(Snail.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Byte> DATA_CRAWL_DIRECTION = SynchedEntityData.defineId(Snail.class, EntityDataSerializers.BYTE);
    private static final double CONTACT = 0.015;
    private final Profile profile;
    private Corner corner;
    private int turnCooldown;

    public Snail(EntityType<? extends Snail> type, Level level) {
        this(type, level, Profile.NORMAL);
    }

    public Snail(EntityType<? extends Snail> type, Level level, Profile profile) {
        super(type, level);
        this.profile = profile;
        setMaxUpStep(0.0F);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Crawl", 0,
                state -> isNoGravity() && getDeltaMovement().lengthSqr() > 1.0E-8
                        ? state.setAndContinue(CRAWL) : PlayState.STOP));
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
        return false;
    }

    public Direction getAttachmentFace() {
        return Direction.from3DDataValue(Byte.toUnsignedInt(entityData.get(DATA_ATTACHMENT)));
    }

    public Direction getCrawlDirection() {
        return Direction.from3DDataValue(Byte.toUnsignedInt(entityData.get(DATA_CRAWL_DIRECTION)));
    }

    private void setAttachmentFace(Direction face) {
        entityData.set(DATA_ATTACHMENT, (byte) face.get3DDataValue());
    }

    private void setCrawlDirection(Direction direction) {
        entityData.set(DATA_CRAWL_DIRECTION, (byte) direction.get3DDataValue());
    }

    @Override
    protected BTRoot createBT() {
        return new BTRoot() {
            @Override
            protected BTNode createTree() {
                return new BTNode() {
                    @Override
                    public BTStatus execute() {
                        navigation.stop();
                        return BTStatus.RUNNING;
                    }
                };
            }
        };
    }

    @Override
    public void travel(Vec3 input) {
        if (!isEffectiveAi()) {
            super.travel(input);
            return;
        }
        if (isInWaterOrBubble() || isInLava()) {
            detach();
            super.travel(input);
            return;
        }
        double speed = Math.max(0.005, getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.18);
        if (corner != null && followCorner(speed)) return;

        Direction face = getAttachmentFace();
        AABB support = findSupport(face, getBoundingBox());
        if (support == null) {
            face = recoverSupport();
            if (face == null) {
                detach();
                setAttachmentFace(Direction.UP);
                super.travel(input);
                return;
            }
            setAttachmentFace(face);
            support = findSupport(face, getBoundingBox());
        }

        Direction crawl = getCrawlDirection();
        if (crawl.getAxis() == face.getAxis()) {
            crawl = face.getAxis().isVertical() ? Direction.NORTH : Direction.UP;
            setCrawlDirection(crawl);
        }
        if (face == Direction.UP && --turnCooldown <= 0) {
            setCrawlDirection(Direction.Plane.HORIZONTAL.getRandomDirection(random));
            crawl = getCrawlDirection();
            turnCooldown = 100 + random.nextInt(160);
        }
        Vec3 step = vector(crawl).scale(speed);
        AABB next = getBoundingBox().move(step);
        if (level().containsAnyLiquid(next)) {
            setCrawlDirection(crawl.getOpposite());
            setDeltaMovement(Vec3.ZERO);
            return;
        }

        if (!level().noCollision(this, next)) {
            crawlMove(step);
            Direction obstacle = crawl.getOpposite();
            if (findSupport(obstacle, getBoundingBox()) != null) {
                setAttachmentFace(obstacle);
                setCrawlDirection(face);
            } else {
                setCrawlDirection(crawl.getOpposite());
            }
        } else if (findSupport(face, next) == null) {
            corner = makeCorner(support, face, crawl);
            if (!followCorner(speed)) {
                setCrawlDirection(crawl.getOpposite());
                setDeltaMovement(Vec3.ZERO);
            }
        } else {
            crawlMove(step);
        }
        updateRotation();
    }

    private Direction recoverSupport() {
        if (findSupport(Direction.UP, getBoundingBox()) != null) return Direction.UP;
        for (Direction direction : Direction.values()) {
            if (findSupport(direction, getBoundingBox()) != null) return direction;
        }
        return null;
    }

    private AABB contactProbe(Direction face, AABB box) {
        double inset = 0.001;
        return switch (face) {
            case UP ->
                    new AABB(box.minX + inset, box.minY - CONTACT, box.minZ + inset, box.maxX - inset, box.minY + inset, box.maxZ - inset);
            case DOWN ->
                    new AABB(box.minX + inset, box.maxY - inset, box.minZ + inset, box.maxX - inset, box.maxY + CONTACT, box.maxZ - inset);
            case EAST ->
                    new AABB(box.minX - CONTACT, box.minY + inset, box.minZ + inset, box.minX + inset, box.maxY - inset, box.maxZ - inset);
            case WEST ->
                    new AABB(box.maxX - inset, box.minY + inset, box.minZ + inset, box.maxX + CONTACT, box.maxY - inset, box.maxZ - inset);
            case SOUTH ->
                    new AABB(box.minX + inset, box.minY + inset, box.minZ - CONTACT, box.maxX - inset, box.maxY - inset, box.minZ + inset);
            case NORTH ->
                    new AABB(box.minX + inset, box.minY + inset, box.maxZ - inset, box.maxX - inset, box.maxY - inset, box.maxZ + CONTACT);
        };
    }

    private AABB findSupport(Direction face, AABB box) {
        AABB probe = contactProbe(face, box);
        AABB best = null;
        double furthest = -Double.MAX_VALUE;
        Direction crawl = getCrawlDirection();
        for (var shape : level().getBlockCollisions(this, probe)) {
            for (AABB part : shape.toAabbs()) {
                if (!part.intersects(probe)) continue;
                double edge = extreme(part, crawl);
                if (edge > furthest) {
                    best = part;
                    furthest = edge;
                }
            }
        }
        return best;
    }

    private Corner makeCorner(AABB support, Direction face, Direction crawl) {
        AABB box = getBoundingBox();
        Vec3 center = box.getCenter();
        Vec3 edge = withProjection(center, crawl, extreme(support, crawl) + halfExtent(box, crawl) + 0.002);
        edge = withProjection(edge, face, extreme(support, face) + halfExtent(box, face) + 0.002);
        Vec3 exit = withProjection(edge, face, extreme(support, face) - halfExtent(box, face) - 0.002);
        return new Corner(support, edge, exit, face, crawl);
    }

    private boolean followCorner(double speed) {
        Corner path = corner;
        if (path == null) return false;
        boolean anchorExists = false;
        for (var shape : level().getBlockCollisions(this, path.support.deflate(0.001))) {
            if (shape.toAabbs().stream().anyMatch(path.support::equals)) {
                anchorExists = true;
                break;
            }
        }
        if (!anchorExists || ++path.age > 600) {
            detach();
            return false;
        }
        Vec3 target = path.rounded ? path.exit : path.edge;
        Vec3 delta = target.subtract(getBoundingBox().getCenter());
        Vec3 step = delta.length() <= speed ? delta : delta.normalize().scale(speed);
        if (!level().noCollision(this, getBoundingBox().move(step)) || level().containsAnyLiquid(getBoundingBox().move(step))) {
            detach();
            return false;
        }
        crawlMove(step);
        if (getBoundingBox().getCenter().distanceToSqr(target) < 1.0E-8) {
            if (!path.rounded) {
                path.rounded = true;
                setAttachmentFace(path.crawl);
                setCrawlDirection(path.face.getOpposite());
            } else {
                corner = null;
                turnCooldown = 100;
            }
        }
        updateRotation();
        return true;
    }

    private void crawlMove(Vec3 movement) {
        setNoGravity(true);
        fallDistance = 0;
        Vec3 before = position();
        move(MoverType.SELF, movement);
        setDeltaMovement(position().subtract(before));
        hasImpulse = true;
    }

    private void detach() {
        boolean wasAttached = isNoGravity() || corner != null;
        corner = null;
        setNoGravity(false);
        if (wasAttached) setDeltaMovement(Vec3.ZERO);
    }

    private void updateRotation() {
        Direction crawl = getCrawlDirection();
        Direction face = getAttachmentFace();
        Direction horizontal = crawl.getAxis().isHorizontal() ? crawl : face;
        float yaw = switch (horizontal) {
            case SOUTH -> 0;
            case WEST -> 90;
            case NORTH -> 180;
            case EAST -> -90;
            default -> getYRot();
        };
        setYRot(yaw);
        setYBodyRot(yaw);
        setYHeadRot(yaw);
        setXRot(crawl == Direction.UP ? -90 : crawl == Direction.DOWN ? 90 : 0);
    }

    private static Vec3 vector(Direction direction) {
        return Vec3.atLowerCornerOf(direction.getNormal());
    }

    private static double extreme(AABB box, Direction direction) {
        return direction.getAxisDirection() == Direction.AxisDirection.POSITIVE
                ? box.max(direction.getAxis()) : -box.min(direction.getAxis());
    }

    private static double halfExtent(AABB box, Direction direction) {
        return (box.max(direction.getAxis()) - box.min(direction.getAxis())) * 0.5;
    }

    private static Vec3 withProjection(Vec3 position, Direction axis, double projection) {
        Vec3 direction = vector(axis);
        return position.add(direction.scale(projection - position.dot(direction)));
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
        detach();
        turnCooldown = 100;
    }

    // 外棱过渡绑定真实碰撞形状：先越过棱边，再沿相邻面下降/上升；支撑消失立即结束。
    private static final class Corner {
        private final AABB support;
        private final Vec3 edge;
        private final Vec3 exit;
        private final Direction face;
        private final Direction crawl;
        private boolean rounded;
        private int age;

        private Corner(AABB support, Vec3 edge, Vec3 exit, Direction face, Direction crawl) {
            this.support = support;
            this.edge = edge;
            this.exit = exit;
            this.face = face;
            this.crawl = crawl;
        }
    }

    public enum Profile {
        NORMAL(false, false), GLOWING(false, true), MAGMA(true, true);
        private final boolean fireImmune;
        private final boolean fullBright;

        Profile(boolean fireImmune, boolean fullBright) {
            this.fireImmune = fireImmune;
            this.fullBright = fullBright;
        }
    }
}
