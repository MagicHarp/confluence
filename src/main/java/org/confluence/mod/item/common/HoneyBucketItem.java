package org.confluence.mod.item.common;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.fluid.ModFluids;
import org.jetbrains.annotations.NotNull;

public class HoneyBucketItem extends BucketItem {
    public HoneyBucketItem() {
        super(ModFluids.HONEY.fluid, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, Level pLevel, @NotNull LivingEntity pEntityLiving) {
        if (!pLevel.isClientSide) {
            pEntityLiving.addEffect(new MobEffectInstance(ModEffects.HONEY.get(), 900));
            pEntityLiving.removeEffect(MobEffects.POISON);
        }
        if (pEntityLiving instanceof ServerPlayer serverplayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, pStack);
            serverplayer.awardStat(Stats.ITEM_USED.get(this));
        }
        if (pEntityLiving instanceof Player && !((Player) pEntityLiving).getAbilities().instabuild) {
            pStack.shrink(1);
        }
        return pStack.isEmpty() ? new ItemStack(Items.BUCKET) : pStack;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 32;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand) {
        InteractionResultHolder<ItemStack> use = super.use(pLevel, pPlayer, pHand);
        if (use.getResult() == InteractionResult.PASS) {
            return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
        }
        return use;
    }
}
