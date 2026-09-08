package org.confluence.mod.common.entity.monster.slime;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import org.confluence.lib.util.LibUtils;
import org.confluence.terra_curio.common.init.TCItems;
import org.confluence.terra_curio.util.TCUtils;

public class IceSlime extends BaseSlime {

    public IceSlime(EntityType<? extends BaseSlime> type, Level level) {
        super(type, level, true);
    }

    @Override
    protected void onAttackTarget(LivingEntity target) {
        if (!TCUtils.hasType(target, TCItems.FROZEN$IMMUNE) && random.nextInt(12) == 0) {
            int duration = LibUtils.isMaster(level(), blockPosition()) ? 500
                    : LibUtils.isAtLeastExpert(level(), blockPosition()) ? 400 : 200;
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration), this);
        }
    }
}
