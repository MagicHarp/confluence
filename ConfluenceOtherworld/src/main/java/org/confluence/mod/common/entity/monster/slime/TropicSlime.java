package org.confluence.mod.common.entity.monster.slime;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

public class TropicSlime extends BaseSlime {

    public TropicSlime(EntityType<? extends BaseSlime> type, Level level) {
        super(type, level, true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createSlimeAttributes(5.0f, 1, 13.0f);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return !source.is(DamageTypes.DROWN) && super.hurt(source, amount);
    }
}
