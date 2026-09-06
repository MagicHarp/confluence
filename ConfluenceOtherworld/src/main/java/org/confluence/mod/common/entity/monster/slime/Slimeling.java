package org.confluence.mod.common.entity.monster.slime;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/// 小史莱姆 —— 腐化史莱姆死亡时分裂出的碎片。
public class Slimeling extends BaseSlime {

    public Slimeling(EntityType<? extends BaseSlime> type, Level level) {
        super(type, level, false, 1);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createSlimeAttributes(7.0f, 2, 45.0f);
    }

    @Override
    protected void onAttackTarget(LivingEntity target) {
        tryApplyDarkness(target);
    }
}
