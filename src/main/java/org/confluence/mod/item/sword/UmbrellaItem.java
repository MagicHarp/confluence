package org.confluence.mod.item.sword;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.item.ModTiers;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;


public class UmbrellaItem extends SwordItem implements CustomModel {
    public UmbrellaItem() {
        super(ModTiers.TITANIUM, 0, -2.4F, new Properties().rarity(ModRarity.BLUE));
    }

    @Override
    public void inventoryTick(@NotNull ItemStack pStack, Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pLevel.isClientSide) return;
        if (pIsSelected && pEntity instanceof LivingEntity living && !living.swinging) {
            living.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 2, 2, false, false, false));
        }
    }
}