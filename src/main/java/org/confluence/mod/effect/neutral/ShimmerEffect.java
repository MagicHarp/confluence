package org.confluence.mod.effect.neutral;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class ShimmerEffect extends MobEffect {
    public ShimmerEffect() {
        super(MobEffectCategory.NEUTRAL, 0xFF96FF);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity living, int pAmplifier) {
        Level level = living.level();
        if (level.isClientSide) return;
        living.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 2, 1, false, false, false));
        if (level.getBlockState(living.getOnPosLegacy()).is(Blocks.BEDROCK) ||
            level.getBlockStates(living.getBoundingBox()).allMatch(blockState -> blockState.liquid() || blockState.isAir())
        ) {
            living.removeEffect(this);
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
