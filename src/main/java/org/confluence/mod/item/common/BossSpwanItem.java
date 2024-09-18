package org.confluence.mod.item.common;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;

public class BossSpwanItem extends Item {
    private final int time;
    Factory bossType;

    public BossSpwanItem(int time, Factory bossType) {
        super(new Properties().rarity(ModRarity.BLUE));
        this.time = time;
        this.bossType = bossType;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide && pLevel.getDayTime() % 24000 >= time) {
            itemStack.shrink(1);
            LivingEntity boss = bossType.create(pLevel);
            if (!pLevel.getEntitiesOfClass(boss.getClass(), pPlayer.getBoundingBox().inflate(Short.MAX_VALUE)).isEmpty()) {
                return InteractionResultHolder.fail(itemStack);
            }
            boss.setPos(pPlayer.getX() + pLevel.random.nextInt(-50, 51), pPlayer.getY(), pPlayer.getZ() + pLevel.random.nextInt(-50, 51));
            pLevel.addFreshEntity(boss);
        }
        return InteractionResultHolder.success(itemStack);
    }

    @FunctionalInterface
    public interface Factory {
        LivingEntity create(Level level);
    }
}
