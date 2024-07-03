package org.confluence.mod.item.common;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.effect.harmful.PotionSicknessEffect;
import org.confluence.mod.item.ModItems;
import org.jetbrains.annotations.NotNull;

public class MushroomItem extends BlockItem implements CustomModel {

    private final float amount;
    Entity entity;

    public MushroomItem(Block pBlock, float amount) {
        super(pBlock, new Properties());
        this.amount = amount;
    }

    @Override
    protected boolean canPlace(@NotNull BlockPlaceContext pContext, @NotNull BlockState pState) {
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getMainHandItem();
        if (pPlayer.hasEffect(ModEffects.POTION_SICKNESS.get())) {
            return InteractionResultHolder.fail(itemStack);
        }
        if (itemStack.is(ModItems.LIFE_MUSHROOM.get())) {
            pPlayer.startUsingItem(pUsedHand);
            pPlayer.heal(amount);
            itemStack.shrink(1);
            PotionSicknessEffect.addTo(pPlayer, 600);
            return InteractionResultHolder.consume(itemStack);
        } else {
            return InteractionResultHolder.fail(itemStack);
        }
    }
}
