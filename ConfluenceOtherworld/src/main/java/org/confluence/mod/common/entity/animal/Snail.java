package org.confluence.mod.common.entity.animal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.common.entity.ai.bt.BTNode;
import org.confluence.mod.common.entity.ai.bt.BTRoot;
import org.confluence.mod.common.entity.ai.bt.BTStatus;
import org.confluence.mod.common.entity.ai.bt.composite.SelectorNode;
import org.confluence.mod.common.entity.ai.bt.leaf.VanillaGoalAction;

/**
 * 能沿地面、墙面和天花板连续爬行的蜗牛类小动物。
 */
public class Snail extends SimpleCritter {
    private static final String ATTACHMENT_TAG = "SnailAttachment";
    private static final String CRAWL_DIRECTION_TAG = "SnailCrawlDirection";
    private static final EntityDataAccessor<Byte> DATA_ATTACHMENT = SynchedEntityData.defineId(Snail.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Byte> DATA_CRAWL_DIRECTION = SynchedEntityData.defineId(Snail.class, EntityDataSerializers.BYTE);
    private final Profile profile;
    private BlockPos outerEdgeSupport;
    private int turnCooldown;
    private int blockedTicks;
    private Vec3 lastCrawlPosition;

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
        AABB box = getBoundingBox();
        double x = Mth.clamp(getX(), box.minX + 1.0E-3, box.maxX - 1.0E-3);
        double y = (box.minY + box.maxY) * 0.5;
        double z = Mth.clamp(getZ(), box.minZ + 1.0E-3, box.maxZ - 1.0E-3);
        double reach = 0.08;
        switch (face) {
            case UP -> y = box.minY - reach;
            case DOWN -> y = box.maxY + reach;
            case NORTH -> z = box.maxZ + reach;
            case SOUTH -> z = box.minZ - reach;
            case WEST -> x = box.maxX + reach;
            case EAST -> x = box.minX - reach;
        }
        BlockPos supportPos = BlockPos.containing(x, y, z);
        BlockState support = level().getBlockState(supportPos);
        return support.isFaceSturdy(level(), supportPos, face);
    }

    private Direction findAdjacentSupport() {
        Direction current = getAttachmentFace();
        if (current.getAxis().isHorizontal()) {
            if (getCrawlDirection() == Direction.DOWN && hasSupport(Direction.UP))
                return Direction.UP;
            if (getCrawlDirection() == Direction.UP && hasSupport(Direction.DOWN))
                return Direction.DOWN;
        }
        if (hasSupport(current)) {
            outerEdgeSupport = null;
            return current;
        }
        // 翻过顶部外棱时，身体最初仍在墙顶上方，中心探针尚未接触墙面。
        // 此时保留已确认的支撑方块，让身体继续下降直至贴上墙面。
        if (outerEdgeSupport != null && current.getAxis().isHorizontal()
                && getCrawlDirection() == Direction.DOWN
                && getBoundingBox().minY <= outerEdgeSupport.getY() + 1.01D
                && getBoundingBox().getCenter().y >= outerEdgeSupport.getY() + 1.0D
                && level().getBlockState(outerEdgeSupport).isFaceSturdy(level(), outerEdgeSupport, current)) {
            return current;
        }
        outerEdgeSupport = null;
        if (current.getAxis().isHorizontal() && getCrawlDirection() == Direction.UP) {
            double topY = topSurfaceYBeyondEdge(current);
            if (Double.isFinite(topY)) {
                return getY() + 1.0E-3D >= topY ? Direction.UP : current;
            }
        }
        if (current == Direction.UP && getCrawlDirection().getAxis().isHorizontal()) {
            Direction travel = getCrawlDirection();
            double outsideCoordinate = outsideWallCoordinateBelowEdge(travel);
            if (Double.isFinite(outsideCoordinate)) {
                // 整个碰撞箱越过外棱之后才转为向下爬墙。
                double coordinate = travel.getAxis() == Direction.Axis.X ? getX() : getZ();
                boolean cleared = travel.getAxisDirection() == Direction.AxisDirection.POSITIVE ? coordinate >= outsideCoordinate : coordinate <= outsideCoordinate;
                return cleared ? travel : current;
            }
        }
        if (current == Direction.DOWN && getCrawlDirection().getAxis().isHorizontal()) {
            Direction travel = getCrawlDirection();
            double outsideCoordinate = outsideCeilingWallCoordinate(travel);
            if (Double.isFinite(outsideCoordinate)) {
                double coordinate = travel.getAxis() == Direction.Axis.X ? getX() : getZ();
                boolean cleared = travel.getAxisDirection() == Direction.AxisDirection.POSITIVE
                        ? coordinate >= outsideCoordinate
                        : coordinate <= outsideCoordinate;
                return cleared ? travel : current;
            }
        }
        if (hasSupport(Direction.UP)) return Direction.UP;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (hasSupport(direction)) return direction;
        }
        return hasSupport(Direction.DOWN) ? Direction.DOWN : null;
    }

    private double topSurfaceYBeyondEdge(Direction wallAttachment) {
        AABB box = getBoundingBox();
        Direction intoWall = wallAttachment.getOpposite();
        double inset = getBbWidth() * 0.5D + 0.08D;
        double x = getX() + intoWall.getStepX() * inset;
        double z = getZ() + intoWall.getStepZ() * inset;
        BlockPos supportPos = BlockPos.containing(x, box.minY - 0.08D, z);
        return level().getBlockState(supportPos).isFaceSturdy(level(), supportPos, Direction.UP) ? supportPos.getY() + 1.0D : Double.NaN;
    }

    private double outsideWallCoordinateBelowEdge(Direction travel) {
        AABB box = getBoundingBox();
        double inset = getBbWidth() * 0.5D + 0.08D;
        double x = getX() - travel.getStepX() * inset;
        double z = getZ() - travel.getStepZ() * inset;
        BlockPos supportPos = BlockPos.containing(x, box.minY - 0.08D, z);
        if (!level().getBlockState(supportPos).isFaceSturdy(level(), supportPos, travel))
            return Double.NaN;
        double clearance = getBbWidth() * 0.5D + 1.0E-3D;
        return switch (travel) {
            case EAST -> supportPos.getX() + 1.0D + clearance;
            case WEST -> supportPos.getX() - clearance;
            case SOUTH -> supportPos.getZ() + 1.0D + clearance;
            case NORTH -> supportPos.getZ() - clearance;
            default -> Double.NaN;
        };
    }

    private double outsideCeilingWallCoordinate(Direction travel) {
        AABB box = getBoundingBox();
        double inset = getBbWidth() * 0.5D + 0.08D;
        BlockPos supportPos = BlockPos.containing(
                getX() - travel.getStepX() * inset,
                box.maxY + 0.08D,
                getZ() - travel.getStepZ() * inset);
        if (!level().getBlockState(supportPos).isFaceSturdy(level(), supportPos, travel))
            return Double.NaN;
        double clearance = getBbWidth() * 0.5D + 1.0E-3D;
        return switch (travel) {
            case EAST -> supportPos.getX() + 1.0D + clearance;
            case WEST -> supportPos.getX() - clearance;
            case SOUTH -> supportPos.getZ() + 1.0D + clearance;
            case NORTH -> supportPos.getZ() - clearance;
            default -> Double.NaN;
        };
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
            setCrawlDirection(getCrawlDirection().getOpposite());
            blockedTicks = 0;
            turnCooldown = 40 + random.nextInt(61);
            return;
        }
        if (--turnCooldown > 0) return;
        Direction current = getCrawlDirection();
        Direction[] candidates = java.util.Arrays.stream(Direction.values())
                .filter(direction -> direction.getAxis() != attachment.getAxis())
                .filter(direction -> direction != current && direction != current.getOpposite())
                .toArray(Direction[]::new);
        if (candidates.length > 0) setCrawlDirection(candidates[random.nextInt(candidates.length)]);
        turnCooldown = 80 + random.nextInt(121);
    }

    private void transitionToAdjacentSupport(Direction previousAttachment, Direction attachment, Direction crawl) {
        setAttachmentFace(attachment);
        if (previousAttachment == Direction.UP && attachment.getAxis().isHorizontal() && crawl == attachment) {
            snapOutsideWall(attachment);
            setCrawlDirection(Direction.DOWN);
            return;
        }
        if (previousAttachment == Direction.DOWN && attachment.getAxis().isHorizontal() && crawl == attachment) {
            snapOutsideCeilingWall(attachment);
            setCrawlDirection(Direction.UP);
            return;
        }
        if (previousAttachment.getAxis().isHorizontal() && attachment == Direction.DOWN && crawl == Direction.DOWN) {
            setCrawlDirection(previousAttachment.getOpposite());
            return;
        }
        if (!previousAttachment.getAxis().isHorizontal() || attachment != Direction.UP) return;
        if (crawl == Direction.UP) {
            double topY = topSurfaceYBeyondEdge(previousAttachment);
            if (Double.isFinite(topY)) setPos(getX(), topY, getZ());
            setCrawlDirection(previousAttachment.getOpposite());
        } else if (crawl == Direction.DOWN) {
            setCrawlDirection(previousAttachment);
        }
    }

    private void snapOutsideWall(Direction wallAttachment) {
        double halfWidth = getBbWidth() * 0.5D;
        double inset = halfWidth + 0.08D;
        BlockPos supportPos = BlockPos.containing(getX() - wallAttachment.getStepX() * inset, getBoundingBox().minY - 0.08D, getZ() - wallAttachment.getStepZ() * inset);
        outerEdgeSupport = supportPos;
        double clearance = halfWidth + 1.0E-3D;
        switch (wallAttachment) {
            case EAST -> setPos(supportPos.getX() + 1.0D + clearance, getY(), getZ());
            case WEST -> setPos(supportPos.getX() - clearance, getY(), getZ());
            case SOUTH -> setPos(getX(), getY(), supportPos.getZ() + 1.0D + clearance);
            case NORTH -> setPos(getX(), getY(), supportPos.getZ() - clearance);
        }
    }

    private void snapOutsideCeilingWall(Direction wallAttachment) {
        double halfWidth = getBbWidth() * 0.5D;
        double inset = halfWidth + 0.08D;
        BlockPos supportPos = BlockPos.containing(
                getX() - wallAttachment.getStepX() * inset,
                getBoundingBox().maxY + 0.08D,
                getZ() - wallAttachment.getStepZ() * inset);
        double clearance = halfWidth + 1.0E-3D;
        switch (wallAttachment) {
            case EAST -> setPos(supportPos.getX() + 1.0D + clearance, getY(), getZ());
            case WEST -> setPos(supportPos.getX() - clearance, getY(), getZ());
            case SOUTH -> setPos(getX(), getY(), supportPos.getZ() + 1.0D + clearance);
            case NORTH -> setPos(getX(), getY(), supportPos.getZ() - clearance);
        }
    }

    private void turnOntoObstacle(Direction attachment, Direction crawl) {
        boolean obstacleAhead = crawl.getAxis().isHorizontal() && hasObstacleAhead(crawl);
        if (attachment == Direction.UP && crawl.getAxis().isHorizontal() && obstacleAhead) {
            setAttachmentFace(crawl.getOpposite());
            setCrawlDirection(Direction.UP);
        } else if (attachment == Direction.DOWN && crawl.getAxis().isHorizontal() && obstacleAhead) {
            setAttachmentFace(crawl.getOpposite());
            setCrawlDirection(Direction.DOWN);
        } else if (attachment.getAxis().isHorizontal()
                && crawl.getAxis().isHorizontal()
                && obstacleAhead) {
            // 沿墙横爬撞到相邻墙面时绕过内棱；否则会一直顶着拐角不动。
            setAttachmentFace(crawl.getOpposite());
            setCrawlDirection(attachment.getOpposite());
        }
    }

    private boolean hasObstacleAhead(Direction crawl) {
        Vec3 step = Vec3.atLowerCornerOf(crawl.getNormal()).scale(Math.max(0.08D, getBbWidth() * 0.25D));
        return !level().noCollision(this, getBoundingBox().move(step));
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
    }

    private final class SurfaceCrawlAction extends BTNode {
        @Override
        public void start() {
            navigation.stop();
            ensureTangentDirection(getAttachmentFace());
            turnCooldown = 60 + random.nextInt(81);
            lastCrawlPosition = position();
        }

        @Override
        public BTStatus execute() {
            if (isInWaterOrBubble() || isInLava()) {
                setNoGravity(false);
                return BTStatus.FAILURE;
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
            updateWanderingDirection(support);
            crawl = getCrawlDirection();
            setNoGravity(true);
            fallDistance = 0.0F;
            double speed = getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.35;
            Vec3 tangent = Vec3.atLowerCornerOf(crawl.getNormal()).scale(speed);
            // 先水平移过顶部外棱，直到整个碰撞箱越过边缘。
            // 此时向下施加贴附力，会让支撑方块的棱角被误判为前方障碍。
            Vec3 adhesion = support == Direction.UP && !hasSupport(Direction.UP)
                    ? Vec3.ZERO
                    : Vec3.atLowerCornerOf(support.getOpposite().getNormal()).scale(0.025);
            setDeltaMovement(tangent.add(adhesion));
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
