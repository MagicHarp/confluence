package org.confluence.mod.common.entity.monster.slime;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import org.confluence.mod.common.entity.projectile.SlimeSpikeEntity;

/// 尖刺丛林史莱姆 —— 远距离瞄准玩家发射尖刺，专家模式近距离改为环形齐射。
public class SpikedJungleSlime extends SpikedSlime {

    public SpikedJungleSlime(EntityType<? extends BaseSlime> type, Level level) {
        super(type, level, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createSlimeAttributes(15.0f, 8, 33.0f);
    }

    @Override
    protected SlimeSpikeEntity.Variant spikeVariant() {
        return SlimeSpikeEntity.Variant.JUNGLE;
    }

    @Override
    protected boolean usesBiomeSpikePattern() {
        return true;
    }
}
