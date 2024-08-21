package org.confluence.mod.entity.worm;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.confluence.mod.util.ModUtils;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractWormEntity extends Monster {
    protected ArrayList<BaseWormPart<? extends AbstractWormEntity>> wormParts;
    private int length;
    private float maxHealth;

    protected BaseWormPart<? extends AbstractWormEntity> partConstructor(int index) {
        ModUtils.testMessage(level(), "partConstructor没有Override，李哉赣神魔");
        return new BaseWormPart<>(null, this, index, maxHealth);
    }
    private void spawnWormParts() {
        for (int i = 0; i < length; i ++) {
            BaseWormPart<? extends AbstractWormEntity> part = partConstructor(i);
            this.wormParts.add(part);
        }
    }
    public AbstractWormEntity(EntityType<? extends AbstractWormEntity> pEntityType, Level pLevel, int length, float maxHealth) {
        super(pEntityType, pLevel);

        try {
            this.maxHealth = maxHealth;
            this.wormParts = new ArrayList<>(length);
            // 生成各体节
            spawnWormParts();
            // 初始化各体节的头/身体/尾节信息
            for (BaseWormPart<? extends AbstractWormEntity> part : wormParts) {
                part.updateSegmentType();
            }
            this.length = length;
        } catch (Exception e) {
            ModUtils.testMessage(level(), e.toString());
        }
        ModUtils.testMessage(level(), "INIT COMPLETE!");
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.get("WormParts") instanceof ListTag listTag) {
            wormParts.clear();
            float maxHealth = getMaxHealth() / length;
            AtomicInteger segIndex = new AtomicInteger();
            listTag.forEach(tag -> {
                if (tag instanceof CompoundTag compoundTag) {
                    BaseWormPart<? extends AbstractWormEntity> part = partConstructor(segIndex.getAndIncrement());
                    part.deserializeNBT(compoundTag);
                    wormParts.add(part);
                }
            });
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

    // 死亡触发的额外机制
    protected void deathCallback() {}
    // 同一种蠕虫的移动特性应当是同样的
    protected WormMovementUtils.WormSegmentMovementOptions getWormFollowOption() {
        ModUtils.testMessage(level(), "getWormFollowOption没有Override，李哉赣神魔");
        return null;
    }
    // AI: 循环遍历所有体节并进行AI判定
    @Override
    public void aiStep() {
        boolean shouldDie = true;
        for (BaseWormPart<? extends AbstractWormEntity> part : wormParts) {
            if (part.isAlive()) {
                shouldDie = false;
                break;
            }
        }
        // TODO: 此方法移除实体是否合理？EnderDragon内部的逻辑类似，但有可能需要调整
        if (shouldDie) {
            deathCallback();
            kill();
            return;
        }

        // 各体节AI
        for (BaseWormPart<? extends AbstractWormEntity> part : wormParts) {
            if (part.isAlive()) {
                part.tickSegment();
                if (part.segmentType == BaseWormPart.SegmentType.HEAD) {
                    WormMovementUtils.handleSegmentsFollow(wormParts, getWormFollowOption(), part.segmentIndex);
                }
            }
        }
    }
}
