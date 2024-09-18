package org.confluence.mod.item.common;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.projectile.bombs.BaseBombEntity;
import org.jetbrains.annotations.NotNull;

public class BombItem extends Item {
    protected float throwSpeed;
    private final Factory factory;

    public BombItem(Factory factory) {
        super(new Properties().rarity(Rarity.COMMON));
        this.throwSpeed = 0.8f;
        this.factory = factory;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            BaseBombEntity bomb = factory.create(player);
            bomb.setOwner(player);
            bomb.setItem(itemStack);
            bomb.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, throwSpeed, 1.0F);
            level.addFreshEntity(bomb);

            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
        }
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide);
    }

    @FunctionalInterface
    public interface Factory {
        BaseBombEntity create(Player player);
    }
}
