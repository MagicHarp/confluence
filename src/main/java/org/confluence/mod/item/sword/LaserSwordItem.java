package org.confluence.mod.item.sword;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class LaserSwordItem extends BoardSwordItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public LaserSwordItem(Tier tier, int rawDamage, float rawSpeed, Properties properties) {
        super(tier, rawDamage, rawSpeed, properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        SoundEvent soundEvent = itemStack.getOrCreateTag().getBoolean("onUse") ? SoundEvents.BEACON_DEACTIVATE : SoundEvents.BEACON_ACTIVATE;
        level.playSound(player, player.getOnPos().above(), soundEvent, SoundSource.PLAYERS, 2, 1);
        return InteractionResultHolder.success(itemStack);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        if (isOnUse(itemStack)) {
            return UseAnim.NONE;
        }
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemStack) {
        if (isOnUse(itemStack)) {
            return 0;
        }
        return 10;
    }

    @Override
    public void releaseUsing(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity living, int tick) {
        if (level.isClientSide) return;
        long id = GeoItem.getOrAssignId(itemStack, (ServerLevel) level);
        if (isOnUse(itemStack)) {
            triggerAnim(living, id, "laser", "pop");
        } else if (tick == 10) {
            triggerAnim(living, id, "laser", "push");
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "laser", 10, state -> PlayState.STOP)
            .triggerableAnim("push", RawAnimation.begin().thenPlayAndHold("laser_push"))
            .triggerableAnim("pop", RawAnimation.begin().thenPlayAndHold("laser_pop"))
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    private boolean isOnUse(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getBoolean("onUse");
    }
}
