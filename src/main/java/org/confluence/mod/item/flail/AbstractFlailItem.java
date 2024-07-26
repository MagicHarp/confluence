package org.confluence.mod.item.flail;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.projectile.FlailEntity;
import org.jetbrains.annotations.NotNull;

public class AbstractFlailItem extends Item {
    public final float damage;
    public final ParticleOptions particle;
    public final MobEffect effect;
    public final double chance;
    public AbstractFlailItem(float damage, ParticleOptions particle, MobEffect effect, double chance){
        super(new Properties().stacksTo(1));
        this.damage = damage;
        this.particle = particle;
        this.effect = effect;
        this.chance = chance;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack){
        return 12000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand){
        pPlayer.startUsingItem(pUsedHand);
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
//
//        if(true){
//            entity = new FlailEntity(ModEntities.FLAIL.get(), pLevel, pPlayer);
//            float radians = (float) Math.toRadians(pPlayer.getYRot());
//            entity.setPos(pPlayer.position().add(-0.6 * Mth.cos(radians), 0, -0.6 * Mth.sin(radians)));
//            pLevel.addFreshEntity(entity);
//        }
        return InteractionResultHolder.success(itemstack);
//        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack){
        return super.canContinueUsing(oldStack, newStack);
    }

    @Override
    public boolean useOnRelease(ItemStack pStack){
//        Confluence.LOGGER.info("release");
//        if(entity != null){
//            entity.discard();
//        }
//        entity = null;
        return super.useOnRelease(pStack);

    }
}
