package org.confluence.mod.entity.worm;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;

import java.util.ArrayList;

public abstract class AbstractWormEntity extends Monster implements GeoEntity {
    protected final ArrayList<BaseWormPart<? extends AbstractWormEntity>> wormParts;
    public ArrayList<BaseWormPart<? extends AbstractWormEntity>> getWormParts() {return wormParts;}
    protected final int TOTAL_LENGTH;
    protected final float MAX_HEALTH;
    protected boolean pendingSegmentSpawn = true;
    public Player target = null;

    /** 子类应该完全覆盖此方法以自定义蠕虫体节的构造器
     * 警告：需要调用setInfo方法！
     *  */
    protected BaseWormPart<? extends AbstractWormEntity> partConstructor(int index) {
        ModUtils.testMessage(level(), "partConstructor没有Override，李哉赣神魔");
        BaseWormPart<? extends AbstractWormEntity> result = new BaseWormPart<>(null, level());
//        result.setInfo(this, index, MAX_HEALTH);
        return result;
    }
    /** 生成一个新的体节并记录在wormParts中 */
    private BaseWormPart<? extends AbstractWormEntity> spawnWormPart() {
        // 无论如何，都记为正在完成体节生成尝试
        pendingSegmentSpawn = false;

        BaseWormPart<? extends AbstractWormEntity> part = partConstructor( this.wormParts.size() );
        this.wormParts.add(part);

        part.moveTo( position() );
        level().addFreshEntity(part);

        return part;
    }
    /** 生成完毕所有体节后调用，初始化各体节的头/身体/尾节信息
     * */
    private void prepareSegments() {
        for (BaseWormPart<? extends AbstractWormEntity> part : wormParts) {
            part.updateSegmentType();
        }
    }
    public AbstractWormEntity(EntityType<? extends AbstractWormEntity> pEntityType, Level pLevel, int totalLength, float maxHealth) {
        super(pEntityType, pLevel);

        this.MAX_HEALTH = maxHealth;
        this.TOTAL_LENGTH = totalLength;
        // 生成体节
        this.wormParts = new ArrayList<>(totalLength);
    }

    @Override
    public boolean isPushable(){
        return false;
    }
    @Override
    public void push(@NotNull Entity pEntity){
    }
    @Override
    protected void pushEntities(){
    }

    @Override
    public void onAddedToWorld(){
        super.onAddedToWorld();
        setNoGravity(true);
        this.noPhysics = true;
    }

    // 蠕虫本身不要因为原版原因受伤；体节可以
    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("WormParts") && pCompound.get("WormParts") instanceof ListTag listTag) {
            // WormPart发现自己不再是wormParts[i]后会悄悄地似了
            wormParts.clear();
            listTag.forEach(tag -> {
                if (tag instanceof CompoundTag compoundTag) {
                    spawnWormPart().deserializeNBT(compoundTag);
                }
            });
            prepareSegments();
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        ListTag listTag = new ListTag();
        for (BaseWormPart<?> part : wormParts) {
            listTag.add(part.serializeNBT());
        }
        pCompound.put("WormParts", listTag);
    }

    /** 死亡触发的额外机制 */
    protected void deathCallback() {}
    // 同一种蠕虫的移动特性应当是同样的
    protected WormMovementUtils.WormSegmentMovementOptions getWormFollowOption() {
        ModUtils.testMessage(level(), "getWormFollowOption没有Override，李哉赣神魔");
        return null;
    }
    // AI: 循环遍历所有体节并进行AI判定
    @Override
    public void tick() {
        super.tick();

        // 为了自定义限度，让后续的AI自行决定哪些功能仅限服务端

        // 需要时生成所有体节
        if (pendingSegmentSpawn) {
            // 在spawnWormPart中pendingSegmentsSpawn被设置为false
            for (int i = 0; i < TOTAL_LENGTH; i++) {
                spawnWormPart();
            }
            prepareSegments();
        }

        // 移除判定
        boolean shouldRemove = true;
        for (BaseWormPart<? extends AbstractWormEntity> part : wormParts) {
            if (part.isAlive()) {
                shouldRemove = false;
                break;
            }
        }
        if (shouldRemove) {
            try {
                deathCallback();
            }
            finally {
                discard();
            }
            return;
        }

        // 移动到所有体节的中间
        Vec3 centerLoc = Vec3.ZERO;
        double totalCount = 0;
        for (BaseWormPart<? extends AbstractWormEntity> seg : wormParts) {
            if (seg.isAlive()) {
                centerLoc = centerLoc.add(seg.position());
                totalCount++;
            }
        }
        if (totalCount > 0) {
            centerLoc = centerLoc.scale( 1 / totalCount );
            teleportTo( centerLoc.x(), centerLoc.y(), centerLoc.z() );
        }

        // 各体节AI
        for (BaseWormPart<? extends AbstractWormEntity> part : wormParts) {
            if (part.isAlive()) {
                part.tickSegment();
                if (part.segmentType == BaseWormPart.SegmentType.HEAD) {
                    WormMovementUtils.handleSegmentsFollow(wormParts, getWormFollowOption(), part.getSegmentIndex());
                }
            }
        }
    }



    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return new ArrayList<>();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot pSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {

    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }
}
