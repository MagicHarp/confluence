package org.confluence.mod.entity.projectile;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.phys.HitResult;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.potion.EffectThrowablePotionItem;
import org.confluence.mod.item.potion.TerraPotions;
import org.jetbrains.annotations.NotNull;

public class EffectThrownPotion extends ThrowableItemProjectile {
    private EffectThrowablePotionItem item;

    public EffectThrownPotion(EntityType<EffectThrownPotion> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public EffectThrownPotion(double pX, double pY, double pZ, Level pLevel) {
        super(ModEntities.EFFECT_THROWN_POTION.get(), pX, pY, pZ, pLevel);
    }

    public EffectThrownPotion(LivingEntity pShooter, Level pLevel) {
        super(ModEntities.EFFECT_THROWN_POTION.get(), pShooter, pLevel);
    }

    @Override
    public void setItem(@NotNull ItemStack pStack) {
        super.setItem(pStack);
        if (pStack.getItem() instanceof EffectThrowablePotionItem effectThrowablePotionItem) {
            this.item = effectThrowablePotionItem;
        }
    }

    @Override
    protected @NotNull EffectThrowablePotionItem getDefaultItem() {
        return TerraPotions.LOVE_POTION.get(EffectThrowablePotionItem.class);
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        Level level = level();
        if (!level.isClientSide) {
            if (item == null) item = getDefaultItem();
            Entity entity = getEffectSource();
            MobEffect pEffect = item.mobEffect.get();
            level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(4.0D, 2.0D, 4.0D)).forEach(living -> {
                if (living.isAffectedByPotions() && distanceToSqr(living) < 16.0) {
                    MobEffectInstance mobEffectInstance = new MobEffectInstance(pEffect, item.duration, item.amplifier);
                    living.addEffect(mobEffectInstance, entity);
                }
            });
            level.levelEvent(LevelEvent.PARTICLES_SPELL_POTION_SPLASH, this.blockPosition(), pEffect.getColor());
            discard();
        }
    }
}
