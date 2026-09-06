package org.confluence.mod.common.entity.monster.slime;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/// 猩红史莱姆 —— 攻击致盲，生成时随机大小。
public class Crimslime extends BaseSlime {

    public Crimslime(EntityType<? extends BaseSlime> type, Level level) {
        super(type, level, false);
        setSlimeSize(1 + random.nextInt(3));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createSlimeAttributes(31.2f, 26, 104.0f);
    }

    @Override
    protected void onAttackTarget(LivingEntity target) {
        tryApplyDarkness(target);
    }
}
