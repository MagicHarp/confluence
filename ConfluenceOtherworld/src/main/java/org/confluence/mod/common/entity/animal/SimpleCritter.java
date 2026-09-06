package org.confluence.mod.common.entity.animal;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.confluence.mod.common.entity.ai.bt.BTNode;
import org.confluence.mod.common.entity.ai.bt.BTRoot;

/// 沿地面短距离移动、停顿并在遇到障碍时改向的被动蠕虫类小动物。
public class SimpleCritter extends BaseCritter {

    public SimpleCritter(EntityType<? extends SimpleCritter> type, Level level) {
        super(type, level);
        getAttribute(Attributes.STEP_HEIGHT).setBaseValue(0.3);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return BaseCritter.createInsectAttributes().add(Attributes.FALL_DAMAGE_MULTIPLIER.value(), 0.0);
    }

    @Override
    protected BTRoot createBT() {
        return new BTRoot() {
            @Override
            protected BTNode createTree() {
                return withPassivePanic(createGroundCritterRoutine(0.45D), 0.7D);
            }
        };
    }

    /// 体型很小的被动蠕虫不承受摔落伤害。
    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }
}
