package org.confluence.mod.common.item.fishing;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.confluence.mod.common.entity.fishing.CurioFishingHook;
import org.confluence.mod.common.item.CustomRarityItem;
import org.confluence.mod.common.item.curio.fishing.FishingBobber;
import org.confluence.mod.mixinauxi.IFishingHook;
import org.confluence.mod.terra_curio.common.component.ModRarity;
import org.confluence.mod.terra_curio.common.util.CuriosUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class AbstractFishingPole extends CustomRarityItem {
    protected static final ImmutableMultimap<Attribute, AttributeModifier> EMPTY = ImmutableMultimap.of();

    public AbstractFishingPole(Properties properties) {
        super(properties);
    }

    public AbstractFishingPole(ModRarity rarity) {
        super(rarity);
    }

    public AbstractFishingPole(Properties properties, ModRarity rarity) {
        super(properties, rarity);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (pPlayer.fishing != null) {
            if (!pLevel.isClientSide) {
                int i = pPlayer.fishing.retrieve(itemstack);
                ItemStack original = itemstack.copy();
                itemstack.hurtAndBreak(i, pPlayer, LivingEntity.getSlotForHand(pHand));
                if (itemstack.isEmpty()) {
                    net.neoforged.neoforge.event.EventHooks.onPlayerDestroyItem(pPlayer, original, pHand);
                }
            }
            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), getRetrieveSound(), SoundSource.NEUTRAL, 1.0F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            pPlayer.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
        } else {
            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), getThrowSound(), SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            if (pLevel instanceof ServerLevel serverLevel) {
                int luckBonus = EnchantmentHelper.getFishingLuckBonus(serverLevel, itemstack, pPlayer);
                int speedBonus = (int) (EnchantmentHelper.getFishingTimeReduction(serverLevel, itemstack, pPlayer) * 20.0F);
                FishingHook fishingHook;
                Optional<FishingBobber> curio = CuriosUtils.findCurio(pPlayer, FishingBobber.class);
                if (curio.isEmpty()) {
                    fishingHook = getHook(itemstack, pPlayer, pLevel, luckBonus, speedBonus);
                } else {
                    fishingHook = new CurioFishingHook(pPlayer, pLevel, luckBonus, speedBonus, curio.get().variant);
                } // todo
//                if (CuriosUtils.noSameCurio(pPlayer, ILavaproofFishingHook.class)) {
//                    if (this == FishingPoleItems.HOTLINE_FISHING_HOOK.get()) {
//                        ((IFishingHook) fishingHook).confluence$setIsLavaHook();
//                    }
//                    pLevel.addFreshEntity(fishingHook);
//                } else {
                    ((IFishingHook) fishingHook).confluence$setIsLavaHook();
                    pLevel.addFreshEntity(fishingHook);
//                }
            }
            pPlayer.awardStat(Stats.ITEM_USED.get(this));
            pPlayer.gameEvent(GameEvent.ITEM_INTERACT_START);
        }
        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide);
    }

    @Override
    public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_FISHING_ROD_ACTIONS.contains(itemAbility);
    }

    protected SoundEvent getRetrieveSound() {
        return SoundEvents.FISHING_BOBBER_RETRIEVE;
    }

    protected SoundEvent getThrowSound() {
        return SoundEvents.FISHING_BOBBER_THROW;
    }

    protected abstract FishingHook getHook(ItemStack itemStack, Player player, Level level, int luckBonus, int speedBonus);
}
