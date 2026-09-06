package org.confluence.mod.common.entity.monster.slime;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import org.confluence.lib.util.LibUtils;
import org.confluence.mod.common.entity.projectile.SlimeSpikeEntity;
import org.confluence.mod.common.init.ModEffects;
import org.confluence.terra_curio.common.init.TCItems;
import org.confluence.terra_curio.util.TCUtils;

/// 尖刺冰雪史莱姆 —— 远距离发射带霜冻效果的冰刺。
public class SpikedIceSlime extends SpikedSlime {

    public SpikedIceSlime(EntityType<? extends BaseSlime> type, Level level) {
        super(type, level, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createSlimeAttributes(6.0f, 8, 31.0f);
    }

    @Override
    protected SlimeSpikeEntity.Variant spikeVariant() {
        return SlimeSpikeEntity.Variant.ICE;
    }

    @Override
    protected boolean usesBiomeSpikePattern() {
        return true;
    }

    @Override
    protected void onAttackTarget(LivingEntity target) {
        if (TCUtils.hasType(target, TCItems.FROZEN$IMMUNE)) return;
        boolean master = LibUtils.isMaster(level(), blockPosition());
        boolean expert = master || LibUtils.isAtLeastExpert(level(), blockPosition());
        int chilledDuration = master ? 1000 : expert ? 800 : 400;
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, chilledDuration), this);
        int frozenChance = expert ? 125 : 15;
        int frozenRolls = expert ? 13 : 1;
        if (random.nextInt(frozenChance) < frozenRolls) {
            target.addEffect(new MobEffectInstance(ModEffects.FROZEN.get(), master ? 50 : expert ? 40 : 20), this);
        }
    }

}
