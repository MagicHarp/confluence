package org.confluence.mod.entity.worm;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;

public abstract class AbstractWormEntity extends LivingEntity {
    protected final ArrayList<BaseWormPart<? extends AbstractWormEntity>> wormParts;
    private final int length;

    public AbstractWormEntity(EntityType<? extends AbstractWormEntity> pEntityType, Level pLevel, int length) {
        super(pEntityType, pLevel);
        this.wormParts = new ArrayList<>(length);
        this.length = length;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.get("WormParts") instanceof ListTag listTag) {
            wormParts.clear();
            float maxHealth = getMaxHealth() / length;
            listTag.forEach(tag -> {
                if (tag instanceof CompoundTag compoundTag) {
                    BaseWormPart<? extends AbstractWormEntity> part = new BaseWormPart<>(this, maxHealth);
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
}
