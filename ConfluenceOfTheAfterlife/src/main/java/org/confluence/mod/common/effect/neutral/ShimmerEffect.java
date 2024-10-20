package org.confluence.mod.common.effect.neutral;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.common.init.ModEffects;

// todo
public class ShimmerEffect extends MobEffect {
    public ShimmerEffect() {
        super(MobEffectCategory.NEUTRAL, 0xFF96FF);
    }

//    @Override
//    public boolean applyEffectTick(@NotNull LivingEntity living, int pAmplifier) {
//        Level level = living.level();
//        if (level.isClientSide) return;
//        if (level.getFluidState(living.getOnPos()).getType().getFluidType() != ModFluids.SHIMMER.fluidType().get()) {
//            living.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 2, 1, false, false, false));
//        }
//        if (level.getBlockState(living.getOnPos()).is(Blocks.BEDROCK) ||
//            level.getBlockStates(living.getBoundingBox().inflate(-0.1)).allMatch(blockState ->
//                (blockState.liquid() && !blockState.is(ModBlocks.SHIMMER.get())) || blockState.isAir())
//        ) {
//            return false;
//        }
//        return true;
//    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }

    public static boolean isInvul(LivingEntity living, DamageSource damageSource) {
        return damageSource.is(DamageTypes.IN_WALL) && living.hasEffect(ModEffects.SHIMMER);
    }
}
