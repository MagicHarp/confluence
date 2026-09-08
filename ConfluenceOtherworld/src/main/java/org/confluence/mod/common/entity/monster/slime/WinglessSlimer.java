package org.confluence.mod.common.entity.monster.slime;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/// 恶翼史莱姆失去翅膀后的地面形态。
///
/// 外观虽与腐化史莱姆一致，但它是恶翼史莱姆的第二形态，不继承腐化史莱姆的分裂语义。
public final class WinglessSlimer extends BaseSlime {
    public WinglessSlimer(EntityType<? extends BaseSlime> type, Level level) {
        super(type, level, false);
    }

    @Override
    protected void onAttackTarget(LivingEntity target) {
        tryApplyDarkness(target);
    }
}
