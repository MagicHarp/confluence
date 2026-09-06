package org.confluence.mod.common.entity.monster.slime;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/// 使用独立夜明纹理的神圣史莱姆变体。
public class LuminousSlime extends BaseSlime {

    public LuminousSlime(EntityType<? extends BaseSlime> type, Level level) {
        super(type, level, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createSlimeAttributes(36.4f, 30, 93.0f);
    }

    @Override
    public boolean isFullBright() {
        return true;
    }
}
